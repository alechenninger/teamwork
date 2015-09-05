package com.github.alechenninger.teamwork.producer;

import com.github.alechenninger.teamwork.MessageType;
import com.github.alechenninger.teamwork.UserName;
import com.github.alechenninger.teamwork.endpoints.UriFactory;

import org.apache.camel.builder.RouteBuilder;

public class ProducerPickUp extends RouteBuilder {
  private final UserName userName;
  private final MessageType messageType;
  private final ProducerPluginUriFactory pluginUriFactory;
  private final UriFactory pickUpUriFactory;

  public ProducerPickUp(UserName userName, MessageType messageType,
      ProducerPluginUriFactory pluginUriFactory, UriFactory pickUpUriFactory) {
    this.userName = userName;
    this.messageType = messageType;
    this.pluginUriFactory = pluginUriFactory;
    this.pickUpUriFactory = pickUpUriFactory;
  }

  @Override
  public void configure() throws Exception {
    from(pickUpUriFactory.forUserAndMessageType(userName, messageType))
        .routeId("producer_pick_up_" + userName + messageType)
        // TODO: Throttling, logging, metrics, reporting
        .to(pluginUriFactory.toProducerPlugin(userName, messageType));

    // TODO: Maybe want to not accept from factory in constructor and instead allow mutability
    // and manage route stop/start here
    // either way we'll have to do that kind of route management somewhere so
    // might want to repurpose this class
  }
}
