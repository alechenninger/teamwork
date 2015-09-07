package com.github.alechenninger.teamwork.endpoints;

import org.apache.camel.CamelContext;
import org.apache.camel.component.http4.HttpClientConfigurer;
import org.apache.camel.component.http4.HttpComponent;

public class HttpConfiguration implements ComponentConfiguration {
  @Override
  public void addToCamelContext(CamelContext context) throws Exception {
    HttpComponent httpComponent = new HttpComponent();
    HttpClientConfigurer configurer = getConfigurer();
    httpComponent.setHttpClientConfigurer(configurer);
    context.addComponent(componentName(), httpComponent);
  }

  public String componentName() {
    return null;
  }

  private HttpClientConfigurer getConfigurer() {
    return null;
  }
}
