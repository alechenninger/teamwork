package com.github.alechenninger.teamwork.hub;

import com.github.alechenninger.teamwork.MessageType;

public interface CanonicalTopicUriFactory {
  String forMessageType(MessageType messageType);
}
