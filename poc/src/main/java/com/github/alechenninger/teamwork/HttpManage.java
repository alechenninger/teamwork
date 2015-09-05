package com.github.alechenninger.teamwork;

import org.apache.camel.builder.RouteBuilder;

public class HttpManage extends RouteBuilder {

  @Override
  public void configure() throws Exception {
    rest("/uris")
        .enableCORS(true);
  }
}
