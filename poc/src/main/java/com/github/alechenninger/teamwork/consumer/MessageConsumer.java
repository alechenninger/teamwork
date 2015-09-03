package com.github.alechenninger.teamwork.consumer;

import org.apache.camel.RoutesBuilder;

public interface MessageConsumer {
  String messageType();

  RoutesBuilder route();

  String version();
}
