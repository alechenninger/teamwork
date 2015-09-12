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

package com.github.alechenninger.teamwork.endpoints;

import com.github.alechenninger.teamwork.Destination;
import com.github.alechenninger.teamwork.MessageType;
import com.github.alechenninger.teamwork.UserName;

public final class DestinationSpecificUriFactory {
  private final UriFactory uriFactory;
  private final Destination destination;

  public DestinationSpecificUriFactory(UriFactory uriFactory, Destination destination) {
    this.uriFactory = uriFactory;
    this.destination = destination;
  }

  public Destination getDestination() {
    return destination;
  }

  public String forUserAndMessageType(UserName userName, MessageType messageType) {
    return uriFactory.forDestination(userName, messageType, destination);
  }
}
