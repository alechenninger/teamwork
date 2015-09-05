package com.github.alechenninger.teamwork.producer;

import com.github.alechenninger.teamwork.MessageType;
import com.github.alechenninger.teamwork.UserName;

public interface ProducerPluginUriFactory {
  String toProducerPlugin(UserName userName, MessageType messageType);
  String fromProducerPlugin(UserName userName, MessageType messageType);
}
