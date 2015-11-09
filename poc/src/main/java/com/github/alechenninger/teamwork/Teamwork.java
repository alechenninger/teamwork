/*
 * Teamwork integration platform.
 * Copyright (C) 2015  Alec Henninger
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.alechenninger.teamwork;

import com.github.alechenninger.teamwork.consumer.ConsumerPluginUriFactory;
import com.github.alechenninger.teamwork.consumer.DirectVmConsumerPluginUriFactory;
import com.github.alechenninger.teamwork.endpoints.UriFactory;
import com.github.alechenninger.teamwork.producer.DirectVmProducerPluginUriFactory;
import com.github.alechenninger.teamwork.producer.PreSortedProducerPickUp;
import com.github.alechenninger.teamwork.producer.ProducerDelivery;
import com.github.alechenninger.teamwork.producer.ProducerPlugin;
import com.github.alechenninger.teamwork.producer.ProducerPluginUriFactory;
import com.github.alechenninger.teamwork.router.ProducerFilter;
import com.github.alechenninger.teamwork.router.CanonicalRouter;
import com.github.alechenninger.teamwork.router.CanonicalRouterRoute;
import com.github.alechenninger.teamwork.router.CanonicalTopicUriFactory;
import com.github.alechenninger.teamwork.router.DirectCanonicalTopicUriFactory;
import com.github.alechenninger.teamwork.router.ProducerFilterRoute;

import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;
import org.apache.camel.CamelContext;
import org.apache.camel.Predicate;
import org.apache.camel.ServiceStatus;
import org.apache.camel.impl.DefaultCamelContextNameStrategy;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Teamwork implements TeamworkApi {
  private final CamelContext teamworkContext;
  // TODO: Need to be more flexible about uri's (multicast to multiple routers, send to different application endpoints...)
  private final UriFactory uriFactory;

  private final Map<UserMessageType, CamelContext> producerContexts = new HashMap<>();
  private final Map<UserMessageType, CamelContext> consumerContexts = new HashMap<>();

  private final Map<UserMessageType, ProducerFilter> producerFilters = new HashMap<>();
  private final Map<MessageType, CanonicalRouter> routers = new HashMap<>();

  // TODO: Inject these or maybe handle some other way
  private final ProducerPluginUriFactory producerUriFactory = new DirectVmProducerPluginUriFactory();
  private final ConsumerPluginUriFactory consumerUriFactory = new DirectVmConsumerPluginUriFactory();
  private final CanonicalTopicUriFactory canonicalTopicUriFactory = new DirectCanonicalTopicUriFactory();

  public Teamwork(UriFactory uriFactory, CamelContext teamworkContext) {
    this.uriFactory = Objects.requireNonNull(uriFactory, "uriFactory");
    this.teamworkContext = Objects.requireNonNull(teamworkContext, "teamworkContext");

    teamworkContext.addLifecycleStrategy(
        new LinkedCamelContextLifecycleStrategy(
            Iterables.concat(producerContexts.values(), consumerContexts.values())));
  }

  @Override
  // TODO: use more specific synchronization primitive instead of method level
  public synchronized void addProducerPlugin(UserName userName, ProducerPlugin plugin) throws Exception {
    MessageType messageType = plugin.messageType();

    // TODO: Handle failure scenarios

    CamelContext newContextForProducer = plugin.createContext(
        producerUriFactory.toProducerPlugin(userName, messageType),
        producerUriFactory.fromProducerPlugin(userName, messageType));

    newContextForProducer.setNameStrategy(new DefaultCamelContextNameStrategy(
        Joiner.on('-').join(userName, "producer", messageType)));

    CamelContext previous = producerContexts.put(
        new UserMessageType(userName, messageType),
        newContextForProducer);

    if (previous == null) {
      PreSortedProducerPickUp pickUpRoute = new PreSortedProducerPickUp(
          userName, messageType, producerUriFactory, uriFactory);
      pickUpRoute.addRoutesToCamelContext(teamworkContext);

      ProducerDelivery deliveryRoute = new ProducerDelivery(
          userName, messageType, producerUriFactory, uriFactory);
      deliveryRoute.addRoutesToCamelContext(teamworkContext);
    } else {
      previous.stop();
    }

    ServiceStatus teamworkContextStatus = teamworkContext.getStatus();
    if (teamworkContextStatus.equals(ServiceStatus.Started) ||
        teamworkContextStatus.equals(ServiceStatus.Starting)) {
      newContextForProducer.start();
    }
  }

  @Override
  public synchronized void addRouter(MessageType messageType, Predicate validator) throws Exception {
    CanonicalRouter current = routers.get(messageType);

    if (current == null) {
      CanonicalRouter router = new CanonicalRouterRoute(messageType, validator,
          canonicalTopicUriFactory, uriFactory);
      router.addRoutesToCamelContext(teamworkContext);
      routers.put(messageType, router);
    } else {
      current.useValidator(validator);
    }
  }

  @Override
  public synchronized void filterProducer(UserName userName, MessageType messageType,
      Predicate filter) throws Exception {
    if (!routers.containsKey(messageType)) {
      throw new IllegalStateException("Must add a router for this message type before filtering a "
          + "producer.");
    }

    UserMessageType userMessageType = new UserMessageType(userName, messageType);
    ProducerFilter current = producerFilters.get(userMessageType);

    if (current == null) {
      ProducerFilter filterRoute = new ProducerFilterRoute(userName, messageType, filter,
          uriFactory, canonicalTopicUriFactory);
      filterRoute.addRoutesToCamelContext(teamworkContext);
      producerFilters.put(userMessageType, filterRoute);
    } else {
      current.filterProducer(filter);
    }
  }

  @Override
  public synchronized void filterConsumer(UserName userName, MessageType messageType,
      Predicate filter) {
    CanonicalRouter router = routers.get(messageType);

    if (router == null) {
      throw new IllegalStateException("Must add a router for this message type before filtering a "
          + "consumer.");
    }

    router.filterConsumer(userName, filter);
  }

  private static class UserMessageType {
    final UserName userName;
    final MessageType messageType;

    UserMessageType(UserName userName, MessageType messageType) {
      this.userName = userName;
      this.messageType = messageType;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      UserMessageType that = (UserMessageType) o;
      return Objects.equals(userName, that.userName) &&
          Objects.equals(messageType, that.messageType);
    }

    @Override
    public int hashCode() {
      return Objects.hash(userName, messageType);
    }
  }
}
