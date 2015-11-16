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

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.util.ExchangeHelper;

public final class PluginProcessor implements Processor {
  private final Plugin plugin;

  public PluginProcessor(Plugin plugin) {
    this.plugin = plugin;
  }

  @Override
  public void process(Exchange exchange) throws Exception {
    Object result = plugin.apply(exchange);

    if (result instanceof Exchange) {
      ExchangeHelper.copyResults(exchange, (Exchange) result);
    } else if (result instanceof Message) {
      // TODO: Should set out? conditionally?
      exchange.setIn((Message) result);
    } else {
      exchange.getIn().setBody(result);
    }
  }
}
