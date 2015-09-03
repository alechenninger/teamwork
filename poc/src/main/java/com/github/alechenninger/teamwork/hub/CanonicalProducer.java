package com.github.alechenninger.teamwork.hub;

import org.apache.camel.Predicate;
import org.apache.camel.builder.RouteBuilder;

// Per user per type
public class CanonicalProducer extends RouteBuilder {
  private final HubUriFactory hubUri;
  private final String userName;
  private final String messageType;
  private final CanonicalTopicUriFactory canonicalTopic;
  private final Predicate producerFilter;

  public CanonicalProducer(String userName, String messageType, Predicate producerFilter,
      HubUriFactory hubUri, CanonicalTopicUriFactory canonicalTopic) {
    this.hubUri = hubUri;
    this.userName = userName;
    this.messageType = messageType;
    this.canonicalTopic = canonicalTopic;
    this.producerFilter = producerFilter;
  }

  @Override
  public void configure() throws Exception {
    from(hubUri.forUserAndMessageType(userName, messageType))
        .choice()
          .when(passesFilter())
          .to(canonicalTopic.forMessageType(messageType))
        .otherwise()
          .log("Message filtered from being produced by " + userName);
  }

  private Predicate passesFilter() {
    return producerFilter;
  }
}
