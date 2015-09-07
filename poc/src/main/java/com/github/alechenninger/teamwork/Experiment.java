package com.github.alechenninger.teamwork;

import org.apache.camel.CamelContext;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.script.ScriptBuilder;
import org.apache.camel.model.ProcessorDefinition;
import org.apache.camel.spi.Policy;
import org.apache.camel.spi.RouteContext;

public class Experiment {
  CamelContext context;

  public void test() {
    new RouteBuilder() {
      @Override
      public void configure() throws Exception {
        from("")
            .filter(simple(""))
            .process(ScriptBuilder.javaScript(""))
            .policy(new Policy() {
              public void beforeWrap(RouteContext routeContext, ProcessorDefinition<?> definition) {

              }

              public Processor wrap(RouteContext routeContext, Processor processor) {
                return null;
              }
            });
      }
    };


  }
}
