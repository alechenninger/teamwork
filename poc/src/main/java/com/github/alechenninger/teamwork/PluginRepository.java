package com.github.alechenninger.teamwork;

import com.github.alechenninger.teamwork.consumer.MessageConsumer;
import com.github.alechenninger.teamwork.producer.MessageProducer;

import org.apache.camel.Predicate;

public interface PluginRepository {
  String getUserName();
  MessageProducer getProducerPlugin(String messageType, String version);
  MessageConsumer getConsumerPlugin(String messageType, String version);
  // TODO: Should these be versioned and "installed" then "activated" like plugins?
  Predicate getFilter(String messageType);
}
