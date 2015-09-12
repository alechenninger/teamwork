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

package com.github.alechenninger.teamwork.consumer;

import com.github.alechenninger.teamwork.MessageType;
import com.github.alechenninger.teamwork.UserName;
import com.github.alechenninger.teamwork.Destination;
import com.github.alechenninger.teamwork.endpoints.UriFactory;

import org.apache.camel.builder.RouteBuilder;

public class ConsumerPickUp extends RouteBuilder {
  private final UserName userName;
  private final MessageType messageType;
  private final UriFactory pickUpUriFactory;
  private final ConsumerPluginUriFactory pluginUriFactory;

  public ConsumerPickUp(UserName userName, MessageType messageType, UriFactory pickUpUriFactory,
      ConsumerPluginUriFactory pluginUriFactory) {
    this.userName = userName;
    this.messageType = messageType;
    this.pickUpUriFactory = pickUpUriFactory;
    this.pluginUriFactory = pluginUriFactory;
  }

  @Override
  public void configure() throws Exception {
    from(pickUpUriFactory.forDestination(userName, messageType, Destination.CONSUMER))
        .routeId("consumer_pick_up_" + userName + "_" + messageType)
        // TODO: logging, metrics, reporting
        .to(pluginUriFactory.toConsumerPlugin(userName, messageType));
  }
}
