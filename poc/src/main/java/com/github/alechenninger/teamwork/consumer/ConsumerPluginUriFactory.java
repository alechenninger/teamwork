package com.github.alechenninger.teamwork.consumer;

public interface ConsumerPluginUriFactory {
  String toConsumerPlugin(String userName, String messageType);
  String fromConsumerPlugin(String userName, String messageType);
}
