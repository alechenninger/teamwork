/*
 * Teamwork integration platform.
 * Copyright (C) 2015  Alec Henninger
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
