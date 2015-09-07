package com.github.alechenninger.teamwork.test;

import com.github.alechenninger.teamwork.MessageType;
import com.github.alechenninger.teamwork.Version;
import com.github.alechenninger.teamwork.producer.ProducerPlugin;

import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.RouteBuilder;

public class NoopProducerPlugin implements ProducerPlugin {
  private final Version version;
  private final MessageType messageType;

  public NoopProducerPlugin(Version version, MessageType messageType) {
    this.version = version;
    this.messageType = messageType;
  }

  @Override
  public Version version() {
    return version;
  }

  @Override
  public MessageType messageType() {
    return messageType;
  }

  @Override
  public RoutesBuilder createRoute(final String fromUri, final String toUri) {
    return new RouteBuilder() {
      @Override
      public void configure() throws Exception {
        from(fromUri)
            .to(toUri);
      }
    };
  }
}
