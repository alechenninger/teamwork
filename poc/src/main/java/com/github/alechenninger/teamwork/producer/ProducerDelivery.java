package com.github.alechenninger.teamwork.producer;

import com.github.alechenninger.teamwork.MessageType;
import com.github.alechenninger.teamwork.UserName;
import com.github.alechenninger.teamwork.Destination;
import com.github.alechenninger.teamwork.endpoints.UriFactory;

import org.apache.camel.builder.RouteBuilder;

public class ProducerDelivery extends RouteBuilder {
  private final UserName userName;
  private final MessageType messageType;
  private final ProducerPluginUriFactory pluginUriFactory;
  private final UriFactory uriFactory;

  public ProducerDelivery(UserName userName, MessageType messageType,
      ProducerPluginUriFactory pluginUriFactory, UriFactory uriFactory) {
    this.userName = userName;
    this.messageType = messageType;
    this.pluginUriFactory = pluginUriFactory;
    this.uriFactory = uriFactory;
  }

  @Override
  public void configure() throws Exception {
    from(pluginUriFactory.fromProducerPlugin(userName, messageType))
        .routeId("producer_delivery_" + userName + messageType)
        // TODO: logging, metrics, reporting
        .to(uriFactory.forDestination(userName, messageType, Destination.ROUTER));
  }
}
