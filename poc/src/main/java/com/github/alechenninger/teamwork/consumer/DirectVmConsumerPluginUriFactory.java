package com.github.alechenninger.teamwork.consumer;

import com.google.common.base.Joiner;

public class DirectVmConsumerPluginUriFactory implements ConsumerPluginUriFactory {
  @Override
  public String toConsumerPlugin(String userName, String messageType) {
    return "direct-vm:" + Joiner.on('_').join("to", "consumer", userName, messageType);
  }

  @Override
  public String fromConsumerPlugin(String userName, String messageType) {
    return "direct-vm:" + Joiner.on('_').join("from", "consumer", userName, messageType);
  }
}
