package com.github.alechenninger.teamwork.producer;

import com.github.alechenninger.teamwork.endpoints.HubUriFactory;

import org.apache.camel.builder.RouteBuilder;

public class ProducerDelivery extends RouteBuilder {
  private final String userName;
  private final String messageType;
  private final ProducerPluginUriFactory pluginUriFactory;
  private final HubUriFactory hubUriFactory;

  public ProducerDelivery(String userName, String messageType,
      ProducerPluginUriFactory pluginUriFactory, HubUriFactory hubUriFactory) {
    this.userName = userName;
    this.messageType = messageType;
    this.pluginUriFactory = pluginUriFactory;
    this.hubUriFactory = hubUriFactory;
  }

  @Override
  public void configure() throws Exception {
    from(pluginUriFactory.fromProducerPlugin(userName, messageType))
        .routeId("producer_delivery_" + userName + messageType)
        // TODO: logging, metrics, reporting
        .to(hubUriFactory.forUserAndMessageType(userName, messageType));
  }
}
