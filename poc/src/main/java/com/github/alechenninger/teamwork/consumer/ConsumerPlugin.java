package com.github.alechenninger.teamwork.consumer;

import com.github.alechenninger.teamwork.MessageType;
import com.github.alechenninger.teamwork.Version;

import org.apache.camel.RoutesBuilder;

public interface ConsumerPlugin {
  MessageType messageType();

  RoutesBuilder route();

  Version version();
}
