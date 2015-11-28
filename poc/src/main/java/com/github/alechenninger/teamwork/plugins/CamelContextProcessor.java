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

package com.github.alechenninger.teamwork.plugins;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.support.ServiceSupport;

public final class CamelContextProcessor extends ServiceSupport implements Processor {
  private final CamelContext pluginContext;
  private final Producer toPlugin;

  public CamelContextProcessor(CamelPluginDefinition pluginDefinition)
      throws Exception {
    pluginContext = pluginDefinition.createCamelContext();
    toPlugin = pluginContext.getEndpoint(pluginDefinition.toContextUri()).createProducer();
  }

  @Override
  public void process(Exchange exchange) throws Exception {
    // TODO: should create new exchange for this?
    exchange.setPattern(ExchangePattern.InOut);

    // TODO: How does async affect transactions? where does retry happen?
    toPlugin.process(exchange);
  }

  @Override
  protected void doStart() throws Exception {
    pluginContext.start();
  }

  @Override
  protected void doStop() throws Exception {
    pluginContext.stop();
  }
}
