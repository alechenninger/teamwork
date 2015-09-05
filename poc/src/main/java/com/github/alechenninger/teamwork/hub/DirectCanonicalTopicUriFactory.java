package com.github.alechenninger.teamwork.hub;

import com.github.alechenninger.teamwork.MessageType;

public final class DirectCanonicalTopicUriFactory implements CanonicalTopicUriFactory {
  @Override
  public String forMessageType(MessageType messageType) {
    return "direct:" + messageType;
  }
}
