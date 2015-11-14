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

package com.github.alechenninger.teamwork.shared;

import org.apache.camel.Exchange;
import org.apache.camel.Expression;
import org.apache.camel.Processor;
import org.apache.camel.support.ExpressionAdapter;

import java.util.UUID;

public abstract class MessageGuid {
  private static final String GUID_HEADER = "teamworkMessageGuid";

  private MessageGuid() {}

  public static Processor ensurePresent() {
    return new Processor() {
      @Override
      public void process(Exchange exchange) throws Exception {
        Object current = exchange.getIn().getHeader(GUID_HEADER);
        if (current == null) {
          exchange.getIn().setHeader(GUID_HEADER, UUID.randomUUID().toString());
        }
      }
    };
  }

  public static Expression expression() {
    return new ExpressionAdapter() {
      @Override
      public String evaluate(Exchange exchange) {
        return exchange.getIn().getHeader(GUID_HEADER, String.class);
      }
    };
  }
}
