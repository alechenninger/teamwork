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

package com.github.alechenninger.teamwork.producer;

import com.github.alechenninger.teamwork.MessageType;
import com.github.alechenninger.teamwork.UserName;
import com.github.alechenninger.teamwork.Destination;
import com.github.alechenninger.teamwork.endpoints.UriFactory;

import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

public class ProducerRoute extends RouteBuilder {
  private final UserName userName;
  private final MessageType messageType;
  private final Processor producerProcessor;
  private final UriFactory uriFactory;

  public ProducerRoute(UserName userName, MessageType messageType,
      Processor producerProcessor, UriFactory uriFactory) {
    this.userName = userName;
    this.messageType = messageType;
    this.producerProcessor = producerProcessor;
    this.uriFactory = uriFactory;
  }

  @Override
  public void configure() throws Exception {
    from(uriFactory.forDestination(userName, messageType, Destination.PRODUCER))
        .routeId("producer_pick_up_" + userName + "_" + messageType)
        // TODO: Throttling, logging, metrics, reporting
        .process(producerProcessor)
        .to(uriFactory.forDestination(userName, messageType, Destination.ROUTER));

    // TODO: Maybe want to not accept from factory in constructor and instead allow mutability
    // and manage route stop/start here
    // either way we'll have to do that kind of route management somewhere so
    // might want to repurpose this class
  }
}
