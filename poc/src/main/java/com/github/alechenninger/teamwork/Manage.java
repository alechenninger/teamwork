package com.github.alechenninger.teamwork;

import com.github.alechenninger.teamwork.consumer.ConsumerPlugin;
import com.github.alechenninger.teamwork.producer.ProducerPlugin;

import org.apache.camel.Predicate;

public interface Manage {
  // TODO: Every mention of message type is really name + version
  // Unless it's just practice to encode version in name (UserV1, UserV2) and they're just treated
  // as different types. But I think you might want some kind of notion of compatibility. Like
  // User v1 and user v2 are compatible, v2 just adds optional new stuff.
  // Also filters probably don't need to be per version? This would mean they need to be compatible
  // with all versions though.

  void addUser(String userName);

  void addProducer(String userName, ProducerPlugin producer);

  void activateProducer(String userName, String messageType, String version);

  // Controls what a producer is allowed to produce. By default this should be "passes validation"
  // (Not necessarily actually, think about handling filtered due to permissions vs would've been
  // allowed if passed validation)
  // but might want to also add something else. Or by default might want to be "filters out
  // everything" so you explicitly allow producing from a user.
  void filterProducer(String userName, String messageType, Predicate filter);

  void addConsumer(String userName, ConsumerPlugin consumer);

  void activateConsumer(String userName, String messageType, String version);

  // Controls what data a consumer has access to
  void filterConsumer(String userName, String messageType, Predicate filter);

  void addMessageType(String messageType, Predicate validator);
}
