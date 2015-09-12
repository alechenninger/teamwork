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

import com.github.alechenninger.teamwork.endpoints.UriFactory;
import com.github.alechenninger.teamwork.producer.DirectVmProducerPluginUriFactory;
import com.github.alechenninger.teamwork.producer.PreSortedProducerPickUp;
import com.github.alechenninger.teamwork.producer.ProducerDelivery;
import com.github.alechenninger.teamwork.producer.ProducerPlugin;
import com.github.alechenninger.teamwork.producer.ProducerPluginUriFactory;

import com.google.common.collect.Iterables;
import org.apache.camel.CamelContext;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.ServiceStatus;
import org.apache.camel.impl.DefaultCamelContext;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Teamwork {
  private final CamelContext teamworkContext;
  // TODO: Need to be more flexible about uri's (multicast to multiple routers, send to different application endpoints...)
  private final UriFactory uriFactory;
  private final Map<UserMessageType, CamelContext> producerContexts = new HashMap<>();
  private final Map<UserMessageType, CamelContext> consumerContexts = new HashMap<>();

  public Teamwork(UriFactory uriFactory, CamelContext teamworkContext) {
    this.uriFactory = Objects.requireNonNull(uriFactory, "uriFactory");
    this.teamworkContext = Objects.requireNonNull(teamworkContext, "teamworkContext");

    teamworkContext.addLifecycleStrategy(
        new LinkedCamelContextLifecycleStrategy(
            Iterables.concat(producerContexts.values(), consumerContexts.values())));
  }

  public void addProducerPlugin(UserName userName, ProducerPlugin plugin) throws Exception {
    ProducerPluginUriFactory pluginUriFactory = new DirectVmProducerPluginUriFactory();
    MessageType messageType = plugin.messageType();
    PreSortedProducerPickUp pickUpRoute = new PreSortedProducerPickUp(userName, messageType,
        pluginUriFactory, uriFactory);
    pickUpRoute.addRoutesToCamelContext(teamworkContext);

    RoutesBuilder pluginRoute = plugin.createRoute(
        pluginUriFactory.toProducerPlugin(userName, messageType),
        pluginUriFactory.fromProducerPlugin(userName, messageType));

    CamelContext producerContext = getProducerContextOrCreate(userName, messageType);
    producerContext.addRoutes(pluginRoute);

    ProducerDelivery deliveryRoute = new ProducerDelivery(userName, messageType, pluginUriFactory, uriFactory);
    deliveryRoute.addRoutesToCamelContext(teamworkContext);
  }

  private CamelContext getProducerContextOrCreate(UserName userName, MessageType messageType) throws Exception {
    UserMessageType userMessageType = new UserMessageType(userName, messageType);
    CamelContext context = producerContexts.get(userMessageType);
    if (context == null) {
      // TODO: context factory? (should we be newing here or should this be more IoC?)
      // Probably should do something like that b/c context has state like registry, properties, etc
      context = new DefaultCamelContext();
      producerContexts.put(userMessageType, context);

      // TODO: handle starting?
      if (teamworkContext.getStatus().equals(ServiceStatus.Started)) {
        context.start();
      }
    }
    return context;
  }

  static class UserMessageType {
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
