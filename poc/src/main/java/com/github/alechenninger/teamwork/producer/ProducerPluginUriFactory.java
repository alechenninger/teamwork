package com.github.alechenninger.teamwork.producer;

public interface ProducerPluginUriFactory {
  String toProducerPlugin(String userName, String messageType);
  String fromProducerPlugin(String userName, String messageType);
}
