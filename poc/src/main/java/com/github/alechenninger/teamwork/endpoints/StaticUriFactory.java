package com.github.alechenninger.teamwork.endpoints;

import com.github.alechenninger.teamwork.Destination;
import com.github.alechenninger.teamwork.MessageType;
import com.github.alechenninger.teamwork.UserName;

/**
 * Always returns same URI for every user name or message type combination.
 */
public final class StaticUriFactory implements UriFactory {
  private final String uri;

  public StaticUriFactory(String uri) {
    this.uri = uri;
  }

  @Override
  public String forDestination(UserName userName, MessageType messageType,
      Destination destination) {
    return uri;
  }
}
