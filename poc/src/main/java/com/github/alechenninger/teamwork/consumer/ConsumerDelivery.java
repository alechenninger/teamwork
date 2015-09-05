package com.github.alechenninger.teamwork.consumer;

import com.github.alechenninger.teamwork.endpoints.ConsumerDeliveryUriFactory;

import org.apache.camel.builder.RouteBuilder;

public class ConsumerDelivery extends RouteBuilder {
  private final String userName;
  private final String messageType;
  private final ConsumerPluginUriFactory pluginUriFactory;
  private final ConsumerDeliveryUriFactory deliveryUriFactory;

  public ConsumerDelivery(String userName, String messageType, ConsumerPluginUriFactory
      pluginUriFactory, ConsumerDeliveryUriFactory deliveryUriFactory) {
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
        .to(deliveryUriFactory.forUserAndMessageType(userName, messageType));
  }
}
