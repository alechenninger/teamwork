package com.github.alechenninger.teamwork.producer;

import com.github.alechenninger.teamwork.MessageType;
import com.github.alechenninger.teamwork.Version;

import org.apache.camel.RoutesBuilder;

public interface ProducerPlugin {
  /**
   * Not version of message type, but version of producer. A message type + version can have
   * multiple producer implementations per user. For example, you are still producing a "User"
   * type of version "1", but you have a bug to fix, so you create another producer with the bug
   * fixed that also handles producing "User" version "1".
   */
  Version version();

  MessageType messageType();

  RoutesBuilder createRoute(String fromUri, String toUri);
}
