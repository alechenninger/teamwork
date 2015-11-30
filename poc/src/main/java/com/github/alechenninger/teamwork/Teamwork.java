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
import com.github.alechenninger.teamwork.producer.ProducerRoute;

import org.apache.camel.CamelContext;
import org.apache.camel.Predicate;
import org.apache.camel.Processor;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

public class Teamwork implements TeamworkApi {
  private final CamelContext teamworkContext;
  // TODO: Need to be more flexible about uri's (multicast to multiple routers, send to different application endpoints...)
  private final UriFactory uriFactory;

  private final Map<UserMessageType, ProducerRoute> producers = new HashMap<>();
  private final Map<MessageType, Router> routers = new HashMap<>();

  public Teamwork(UriFactory uriFactory, CamelContext teamworkContext) {
    this.uriFactory = Objects.requireNonNull(uriFactory, "uriFactory");
    this.teamworkContext = Objects.requireNonNull(teamworkContext, "teamworkContext");
  }

  @Override
  // TODO: use more specific synchronization primitive instead of method level
  public synchronized void addProducerPlugin(UserName userName, MessageType messageType,
      Processor producerProcessor) throws Exception {
    // TODO: Handle failure scenarios

  }

  @Override
  public synchronized Router addRouter(MessageType messageType, Predicate validator) throws Exception {
    if (routers.containsKey(messageType)) {
      // TODO: Could consider not failing here and just updating validator instead
      throw new IllegalArgumentException("Router already exists for message type: " + messageType);
    }

    Router router = new TeamworkRouter(messageType, validator, teamworkContext, uriFactory);

    routers.put(messageType, router);

    return router;
  }

  @Override
  public Router getRouter(MessageType messageType) {
    Router router = routers.get(messageType);

    if (router == null) {
      throw new NoSuchElementException("No router exists for message type: " + messageType);
    }

    return router;
  }
}
