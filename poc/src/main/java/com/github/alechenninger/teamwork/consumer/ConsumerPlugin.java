package com.github.alechenninger.teamwork.consumer;

import org.apache.camel.RoutesBuilder;

public interface ConsumerPlugin {
  String messageType();

  RoutesBuilder route();

  String version();
}
