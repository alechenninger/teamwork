package com.github.alechenninger.teamwork.consumer;

import com.github.alechenninger.teamwork.MessageType;
import com.github.alechenninger.teamwork.UserName;

public interface ConsumerPluginUriFactory {
  String toConsumerPlugin(UserName userName, MessageType messageType);
  String fromConsumerPlugin(UserName userName, MessageType messageType);
}
