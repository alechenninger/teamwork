package com.github.alechenninger.teamwork;

import com.github.alechenninger.teamwork.consumer.ConsumerPlugin;
import com.github.alechenninger.teamwork.producer.ProducerPlugin;

import org.apache.camel.Predicate;

public interface Repository {
  UserName getUserName();
  ProducerPlugin getProducerPlugin(MessageType messageType, Version version);
  ConsumerPlugin getConsumerPlugin(MessageType messageType, Version version);
  // TODO: Should these be versioned and "installed" then "activated" like plugins?
  Predicate getFilter(MessageType messageType);
  // TODO: Including mapping of predicates to message types?
  // This gets into whether or not message types should be "presorted" (already sorted by queue)
  // or all dumped in one bucket then resorted again.
  // One bucket allows easier consume with priority and throttling maybe?
  // But what is the downside to consume with priority? Can't we simply allocate more/less JMS
  // consumers to control priority?
}
