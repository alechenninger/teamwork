package com.github.alechenninger.teamwork.producer;

import com.github.alechenninger.teamwork.MessageType;
import com.github.alechenninger.teamwork.UserName;

import com.google.common.base.Joiner;

public final class DirectVmProducerPluginUriFactory implements ProducerPluginUriFactory {
  @Override
  public String toProducerPlugin(UserName userName, MessageType messageType) {
    return "direct-vm:" + Joiner.on('_').join("to", "producer", userName, messageType);
  }

  @Override
  public String fromProducerPlugin(UserName userName, MessageType messageType) {
    return "direct-vm:" + Joiner.on('_').join("from", "producer", userName, messageType);
  }
}
