package com.github.alechenninger.teamwork;

import com.github.alechenninger.teamwork.consumer.ConsumerPlugin;
import com.github.alechenninger.teamwork.producer.ProducerPlugin;

import org.apache.camel.Predicate;

public interface Manage {
  void addUser(UserName userName);

  void addProducer(UserName userName, ProducerPlugin producer);

  void activateProducer(UserName userName, MessageType messageType, Version version);

  // Controls what a producer is allowed to produce
  void filterProducer(UserName userName, MessageType messageType, Predicate filter);

  void addConsumer(UserName userName, ConsumerPlugin consumer);

  void activateConsumer(UserName userName, MessageType messageType, Version version);

  // Controls what data a consumer has access to
  void filterConsumer(UserName userName, MessageType messageType, Predicate filter);

  void addMessageType(MessageType messageType, Predicate validator);
}
