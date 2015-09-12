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
 * Uses a set component and context path, but varies an option based on user name and message type.
 *
 * <p>For example, the kafka component uses the topic as an option.
 *
 * <p>TODO: Allow passing template string so option can be formatted however
 */
public final class OptionBasedUriFactory implements UriFactory {
  private final String component;
  private final String path;
  private final Map<String, String> options;
  private final String optionOfInterest;

  public OptionBasedUriFactory(String component, String path, Map<String, String> options, String
      optionOfInterest) {
    this.component = component;
    this.path = path;
    this.options = new HashMap<>(options);
    this.optionOfInterest = optionOfInterest;
  }

  @Override
  public String forDestination(UserName userName, MessageType messageType,
      Destination destination) {
    Map<String, String> generatedOpts = new HashMap<>(options);
    generatedOpts.put(optionOfInterest, Joiner.on('_').join(destination, userName, messageType));
    return component + ":" + path + "?" + Joiner.on('&').withKeyValueSeparator("=").join(options);
  }
}
