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
