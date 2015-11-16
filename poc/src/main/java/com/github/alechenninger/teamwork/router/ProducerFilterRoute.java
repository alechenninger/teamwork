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

package com.github.alechenninger.teamwork.router;

import com.github.alechenninger.teamwork.MessageType;
import com.github.alechenninger.teamwork.UserName;
import com.github.alechenninger.teamwork.Destination;
import com.github.alechenninger.teamwork.endpoints.UriFactory;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Predicate;
import org.apache.camel.builder.RouteBuilder;

// Per user per type
public class ProducerFilterRoute extends RouteBuilder implements ProducerFilter {
  private final UriFactory uriFactory;
  private final UserName userName;
  private final MessageType messageType;
  private final RouterUriFactory routerUriFactory;

  private Predicate producerFilter;

  public ProducerFilterRoute(UserName userName, MessageType messageType, Predicate producerFilter,
      UriFactory uriFactory, RouterUriFactory routerUriFactory) {
    this.uriFactory = uriFactory;
    this.userName = userName;
    this.messageType = messageType;
    this.routerUriFactory = routerUriFactory;
    this.producerFilter = producerFilter;
  }

  @Override
  public void filterProducer(Predicate filter) {
    producerFilter = filter;
  }

  @Override
  public void configure() throws Exception {
    from(uriFactory.forDestination(userName, messageType, Destination.ROUTER))
        .routeId(routeId())
        .choice()
          // TODO: Should allow filters to say why they didn't pass a la Hamcrest matcher describeMismatch
          // Similar to router, wrap in closure to pickup changes in filter
          .when(exchange -> producerFilter.matches(exchange))
          .to(routerUriFactory.forMessageType(messageType))
        .otherwise()
          .log("Message filtered from being produced by " + userName);
  }

  @Override
  public void removeRoutesFromCamelContext(CamelContext context) throws Exception {
    context.removeRoute(routeId());
  }

  private String routeId() {
    return "teamwork-canonical-producer:" + userName + messageType;
  }
}
