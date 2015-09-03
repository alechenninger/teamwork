package com.github.alechenninger.teamwork.producer;

import org.apache.camel.RoutesBuilder;

public interface MessageProducer {
  /**
   * Not version of message type, but version of producer. A message type + version can have
   * multiple producer implementations per user. For example, you are still producing a "User"
   * type
   * of version "1", but you have a bug to fix, so you create another producer with the bug fixed
   * that also handles producing "User" version "1".
   */
  String version();

  String messageType();

  RoutesBuilder route(String fromUri, String toUri);
}
