package com.github.alechenninger.teamwork.consumer;

import com.github.alechenninger.teamwork.MessageType;
import com.github.alechenninger.teamwork.UserName;

import com.google.common.base.Joiner;

public class DirectVmConsumerPluginUriFactory implements ConsumerPluginUriFactory {
  @Override
  public String toConsumerPlugin(UserName userName, MessageType messageType) {
    return "direct-vm:" + Joiner.on('_').join("to", "consumer", userName, messageType);
  }

  @Override
  public String fromConsumerPlugin(UserName userName, MessageType messageType) {
    return "direct-vm:" + Joiner.on('_').join("from", "consumer", userName, messageType);
  }
}
