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

import org.apache.camel.Predicate;
import org.apache.camel.builder.RouteBuilder;

// Per user per type
public class CanonicalProducer extends RouteBuilder {
  private final UriFactory hubUri;
  private final UserName userName;
  private final MessageType messageType;
  private final CanonicalTopicUriFactory canonicalTopic;
  private final Predicate producerFilter;

  public CanonicalProducer(UserName userName, MessageType messageType, Predicate producerFilter,
      UriFactory hubUri, CanonicalTopicUriFactory canonicalTopic) {
    this.hubUri = hubUri;
    this.userName = userName;
    this.messageType = messageType;
    this.canonicalTopic = canonicalTopic;
    this.producerFilter = producerFilter;
  }

  @Override
  public void configure() throws Exception {
    from(hubUri.forDestination(userName, messageType, Destination.ROUTER))
        .choice()
          .when(passesFilter())
          .to(canonicalTopic.forMessageType(messageType))
        .otherwise()
          .log("Message filtered from being produced by " + userName);
  }

  private Predicate passesFilter() {
    return producerFilter;
  }
}
