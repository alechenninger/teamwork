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
import com.github.alechenninger.teamwork.router.RouterUriFactory;
import com.github.alechenninger.teamwork.router.ProducerFilter;
import com.github.alechenninger.teamwork.router.ProducerFilterRoute;
import com.github.alechenninger.teamwork.router.RouterRoute;

import org.apache.camel.CamelContext;
import org.apache.camel.Predicate;

import java.util.HashMap;
import java.util.Map;

public class TeamworkRouter implements Router {
  private final MessageType messageType;
  private final RouterRoute routerRoute;
  private final Map<UserMessageType, ProducerFilter> producerFilters = new HashMap<>();
  private final CamelContext teamworkContext;
  private final UriFactory uriFactory;
  private final RouterUriFactory routerUriFactory;

  public TeamworkRouter(MessageType messageType, Predicate validator, CamelContext teamworkContext,
      UriFactory uriFactory, RouterUriFactory routerUriFactory) throws Exception {
    this.messageType = messageType;
    this.routerRoute = new RouterRoute(messageType, validator, routerUriFactory, uriFactory);
    this.teamworkContext = teamworkContext;
    this.uriFactory = uriFactory;
    this.routerUriFactory = routerUriFactory;

    routerRoute.addRoutesToCamelContext(teamworkContext);
  }

  @Override
  public void useValidator(Predicate validator) {
    routerRoute.useValidator(validator);
  }

  @Override
  public synchronized void filterProducer(UserName userName, Predicate filter) throws Exception {
    UserMessageType userMessageType = new UserMessageType(userName, messageType);
    ProducerFilter current = producerFilters.get(userMessageType);

    if (current == null) {
      ProducerFilter filterRoute = new ProducerFilterRoute(userName, messageType, filter,
          uriFactory, routerUriFactory);
      filterRoute.addRoutesToCamelContext(teamworkContext);
      producerFilters.put(userMessageType, filterRoute);
    } else {
      current.filterProducer(filter);
    }
  }

  @Override
  public synchronized void filterConsumer(UserName userName, Predicate filter) {
    routerRoute.filterConsumer(userName, filter);
  }
}
