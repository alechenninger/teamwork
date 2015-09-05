package com.github.alechenninger.teamwork.consumer;

import com.github.alechenninger.teamwork.MessageType;
import com.github.alechenninger.teamwork.UserName;
import com.github.alechenninger.teamwork.endpoints.ConsumerPickUpUriFactory;

import org.apache.camel.builder.RouteBuilder;

public class ConsumerPickUp extends RouteBuilder {
  private final UserName userName;
  private final MessageType messageType;
  private final ConsumerPickUpUriFactory pickUpUriFactory;
  private final ConsumerPluginUriFactory pluginUriFactory;

  public ConsumerPickUp(UserName userName, MessageType messageType, ConsumerPickUpUriFactory
      pickUpUriFactory, ConsumerPluginUriFactory pluginUriFactory) {
    this.userName = userName;
    this.messageType = messageType;
    this.pickUpUriFactory = pickUpUriFactory;
    this.pluginUriFactory = pluginUriFactory;
  }

  @Override
  public void configure() throws Exception {
    from(pickUpUriFactory.forUserAndMessageType(userName, messageType))
        .routeId("consumer_pick_up_" + userName + messageType)
        // TODO: logging, metrics, reporting
        .to(pluginUriFactory.toConsumerPlugin(userName, messageType));
  }
}
