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
import com.github.alechenninger.teamwork.Version;

import org.apache.camel.RoutesBuilder;

public interface ProducerPlugin {
  /**
   * Not version of message type, but version of producer. A message type + version can have
   * multiple producer implementations per user. For example, you are still producing a "User"
   * type of version "1", but you have a bug to fix, so you create another producer with the bug
   * fixed that also handles producing "User" version "1".
   */
  Version version();

  MessageType messageType();

  RoutesBuilder createRoute(String fromUri, String toUri);
}
