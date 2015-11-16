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
import org.apache.camel.support.ExpressionAdapter;

public final class PluginExpression extends ExpressionAdapter {
  private final Plugin plugin;

  public PluginExpression(Plugin plugin) {
    this.plugin = plugin;
  }

  @Override
  public Object evaluate(Exchange exchange) {
    try {
      return plugin.apply(exchange);
    } catch (Exception e) {
      // TODO: Is FailureResult a good idea? Or null? Or runtimeexception?
      return new FailureResult(e);
    }
  }
}
