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

import com.google.common.base.Joiner;

import java.util.HashMap;
import java.util.Map;

/**
 * Uses a static camel component (URI scheme) and options for every URI. The path of the URI changes
 * based on the user and message type.
 *
 * <p>TODO: Allow passing template string so option can be formatted however
 */
public final class PathBasedUriFactory implements UriFactory {
  private final String component;
  private final Map<String, String> options;

  public PathBasedUriFactory(String component, Map<String, String> options) {
    this.component = component;
    this.options = new HashMap<>(options);
  }

  @Override
  public String forDestination(UserName userName, MessageType messageType,
      Destination destination) {
    if (options.isEmpty()) {
      return component + ":" + Joiner.on('_').join(destination, userName, messageType);
    }

    return component +
        ":" + Joiner.on('_').join(destination, userName, messageType) +
        "?" + Joiner.on('&').withKeyValueSeparator("=").join(options);
  }
}
