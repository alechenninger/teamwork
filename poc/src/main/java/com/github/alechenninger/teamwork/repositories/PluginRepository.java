package com.github.alechenninger.teamwork.repositories;

import com.github.alechenninger.teamwork.MessageType;
import com.github.alechenninger.teamwork.UserName;
import com.github.alechenninger.teamwork.Version;
import com.github.alechenninger.teamwork.consumer.ConsumerPlugin;
import com.github.alechenninger.teamwork.producer.ProducerPlugin;

import java.util.Set;

public interface PluginRepository {
  void addProducerPlugin(UserName userName, ProducerPlugin plugin);

  void addConsumerPlugin(UserName userName, ConsumerPlugin plugin);

  // TODO: May want to return different data structure for these; perhaps map or table
  Set<ProducerPlugin> getProducerPluginsForUser(UserName userName);

  Set<ConsumerPlugin> getConsumerPluginsForUser(UserName userName);

  ProducerPlugin getProducerPlugin(UserName userName, MessageType messageType, Version version);

  ConsumerPlugin getConsumerPlugin(UserName userName, MessageType messageType, Version version);
}
