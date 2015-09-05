package com.github.alechenninger.teamwork.consumer;

import com.github.alechenninger.teamwork.MessageType;
import com.github.alechenninger.teamwork.UserName;
import com.github.alechenninger.teamwork.endpoints.Destination;
import com.github.alechenninger.teamwork.endpoints.UriFactory;

import org.apache.camel.builder.RouteBuilder;

public class ConsumerDelivery extends RouteBuilder {
  private final UserName userName;
  private final MessageType messageType;
  private final ConsumerPluginUriFactory pluginUriFactory;
  private final UriFactory deliveryUriFactory;

  public ConsumerDelivery(UserName userName, MessageType messageType, ConsumerPluginUriFactory
      pluginUriFactory, UriFactory deliveryUriFactory) {
    this.userName = userName;
    this.messageType = messageType;
    this.pluginUriFactory = pluginUriFactory;
    this.deliveryUriFactory = deliveryUriFactory;
  }

  @Override
  public void configure() throws Exception {
    from(pluginUriFactory.fromConsumerPlugin(userName, messageType))
        .routeId("consumer_delivery_" + userName + messageType)
        // TODO: logging, metrics, reporting
        .to(deliveryUriFactory.forDestination(userName, messageType, Destination.APPLICATION));
  }
}
