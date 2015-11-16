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
import org.apache.camel.Processor;

public final class ProcessorPlugin implements Plugin {
  private final Processor processor;
  private final Class<?> resultType;

  public ProcessorPlugin(Processor processor, Class<?> resultType) {
    this.processor = processor;
    this.resultType = resultType;
  }

  @Override
  public Object apply(Exchange exchange) throws Exception {
    Exchange resultExchange = exchange.copy();

    processor.process(resultExchange);

    return resultExchange.hasOut()
        ? resultExchange.getOut().getMandatoryBody(resultType)
        : resultExchange.getIn().getMandatoryBody(resultType);
  }
}
