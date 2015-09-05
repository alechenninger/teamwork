package com.github.alechenninger.teamwork.hub;

import com.github.alechenninger.teamwork.MessageType;
import com.github.alechenninger.teamwork.UserName;
import com.github.alechenninger.teamwork.endpoints.UriFactory;

import org.apache.camel.Predicate;
import org.apache.camel.builder.RouteBuilder;

// Per user per type
public class CanonicalProducer extends RouteBuilder {
  private final UriFactory hubUri;
  private final UserName userName;
  private final MessageType messageType;
  private final CanonicalTopicUriFactory canonicalTopic;
  private final Predicate producerFilter;

  public CanonicalProducer(UserName userName, MessageType messageType, Predicate producerFilter,
      UriFactory hubUri, CanonicalTopicUriFactory canonicalTopic) {
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
