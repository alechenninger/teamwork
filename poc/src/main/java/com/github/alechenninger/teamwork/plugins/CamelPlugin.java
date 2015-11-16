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
import org.apache.camel.Producer;

public final class CamelPlugin implements Plugin, RequiresStart, RequiresStop {
  private final CamelContext pluginContext;
  private final Producer toPlugin;

  public CamelPlugin(CamelPluginDefinition pluginDefinition, CamelContext teamworkContext)
      throws Exception {
    // TODO: Allow multiple of same plugin per vm? What if names aren't unique?
    String fromUri = "direct-vm:to-plugin-" + pluginDefinition.name();

    pluginContext = pluginDefinition.createCamelContext(fromUri);

    toPlugin = teamworkContext.getEndpoint(fromUri).createProducer();
  }

  @Override
  public Exchange apply(Exchange exchange) throws Exception {
    // TODO: should create new exchange for this?
    exchange.setPattern(ExchangePattern.InOut);

    // TODO: How does async affect transactions? where does retry happen?
    toPlugin.process(exchange);

    return exchange;
  }

  @Override
  public void start() throws Exception {
    pluginContext.start();
  }

  @Override
  public void stop() throws Exception {
    pluginContext.stop();
  }
}
