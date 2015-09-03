package com.github.alechenninger.teamwork.templates;

import org.apache.camel.builder.RouteBuilder;

public class XmlProducerRoute extends RouteBuilder {
  private final String fromUri;
  private final String toUri;
  private final String xsdLocation;
  private final String xsltLocation;

  public XmlProducerRoute(String fromUri, String toUri, String xsdLocation, String xsltLocation) {
    this.fromUri = fromUri;
    this.toUri = toUri;
    this.xsdLocation = xsdLocation;
    this.xsltLocation = xsltLocation;
  }

  @Override
  public void configure() throws Exception {
    from(fromUri)
        .to("validate:" + xsdLocation)
        .to("xsl:" + xsltLocation)
        .to(toUri);
  }
}
