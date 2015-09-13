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
import com.github.alechenninger.teamwork.Version;

import org.apache.camel.CamelContext;
import org.apache.camel.RoutesBuilder;

// TODO: Unify ConsumerPlugin and ProducerPlugin interfaces?
public interface ConsumerPlugin {
  MessageType messageType();

  /**
   * This is how plugins provide functionality: an isolated context which communicates with Teamwork
   * via supplied URIs. Plugins are expected to add routes and whatever else they need to the
   * context before returning it. Plugins do not have to start the context (and probably shouldn't).
   *
   * @param fromUri The URI this context can expect messages to be sent to from Teamwork.
   * @param toUri The URI Teamwork expects this context to send processed messages to.
   */
  CamelContext createContext(String fromUri, String toUri);
}
