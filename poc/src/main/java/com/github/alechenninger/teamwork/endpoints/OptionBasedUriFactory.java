package com.github.alechenninger.teamwork.endpoints;

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
