package com.github.alechenninger.teamwork.endpoints;

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
