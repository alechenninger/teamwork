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
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class AsyncCamelPluginTest extends CamelTestSupport {
  private final CamelPluginDefinition asyncPluginDefinition = new CamelPluginDefinition() {
    @Override
    public CamelContext createCamelContext(String toPluginUri) throws Exception {
      CamelContext context = new DefaultCamelContext();

      new RouteBuilder() {
        @Override
        public void configure() throws Exception {
          from(toPluginUri)
              .to("seda:test");

          from("seda:test")
              .setBody(constant("reply"));
        }
      }.addRoutesToCamelContext(context);

      return context;
    }

    @Override
    public String name() {
      return null;
    }
  };

  private CamelPlugin plugin;

  @Override
  protected void doPostSetup() throws Exception {
    plugin = new CamelPlugin(asyncPluginDefinition, context);
    plugin.start();
  }

  @After
  public void stopPlugin() throws Exception {
    plugin.stop();
  }

  @Test
  public void testAsyncReply() throws Exception {
    Exchange source = createExchangeWithBody("foo");
    Exchange result = plugin.apply(source);
    if (result.hasOut()) {
      assertEquals("reply", result.getOut().getBody());
    } else {
      assertEquals("reply", result.getIn().getBody());
    }
  }
}
