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
