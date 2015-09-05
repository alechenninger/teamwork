package com.github.alechenninger.teamwork.endpoints;

import com.github.alechenninger.teamwork.MessageType;
import com.github.alechenninger.teamwork.UserName;

/**
 * Defines the entry point for a producer route.
 */
public interface ProducerPickUpUriFactory {
  String forUserAndMessageType(UserName userName, MessageType messageType);
}
