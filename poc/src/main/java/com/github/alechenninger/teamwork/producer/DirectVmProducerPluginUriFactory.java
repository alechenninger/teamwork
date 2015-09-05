package com.github.alechenninger.teamwork.producer;

import com.google.common.base.Joiner;

public class DirectVmProducerPluginUriFactory implements ProducerPluginUriFactory {
  @Override
  public String toProducerPlugin(String userName, String messageType) {
    return "direct-vm:" + Joiner.on('_').join("to", "producer", userName, messageType);
  }

  @Override
  public String fromProducerPlugin(String userName, String messageType) {
    return "direct-vm:" + Joiner.on('_').join("from", "producer", userName, messageType);
  }
}
