package com.github.alechenninger.teamwork;

import org.apache.camel.builder.RouteBuilder;

public class TestProducer extends RouteBuilder {
  @Override
  public void configure() throws Exception {
    from("foo")
        .setBody(constant("bar"));
  }
}
