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

package com.github.alechenninger.teamwork.test;

import com.github.alechenninger.teamwork.MessageType;
import com.github.alechenninger.teamwork.producer.ProducerPlugin;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class NoopProducerPlugin implements ProducerPlugin {
  private final MessageType messageType;

  public NoopProducerPlugin(MessageType messageType) {
    this.messageType = messageType;
  }

  @Override
  public MessageType messageType() {
    return messageType;
  }

  @Override
  public CamelContext createContext(final String fromUri, final String toUri) throws Exception {
    CamelContext context = new DefaultCamelContext();
    new RouteBuilder() {
      @Override
      public void configure() throws Exception {
        from(fromUri)
            .to(toUri);
      }
    }.addRoutesToCamelContext(context);
    return context;
  }
}
