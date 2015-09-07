package com.github.alechenninger.teamwork.router;

import com.github.alechenninger.teamwork.MessageType;

public interface CanonicalTopicUriFactory {
  String forMessageType(MessageType messageType);
}
