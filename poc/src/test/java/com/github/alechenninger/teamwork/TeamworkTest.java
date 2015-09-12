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

package com.github.alechenninger.teamwork;

import com.github.alechenninger.teamwork.endpoints.PathBasedUriFactory;
import com.github.alechenninger.teamwork.endpoints.UriFactory;
import com.github.alechenninger.teamwork.producer.ProducerPlugin;
import com.github.alechenninger.teamwork.test.NoopProducerPlugin;

import org.apache.camel.EndpointInject;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.impl.ExplicitCamelContextNameStrategy;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Collections;

@RunWith(JUnit4.class)
public class TeamworkTest extends CamelTestSupport {
  private UriFactory uriFactory = new PathBasedUriFactory("direct", Collections.<String, String>emptyMap());

  private Teamwork teamwork;
  private UserName knuth  = new UserName("knuth");
  private MessageType quoteV1 = new MessageType("Quote", Version.v1_0_0());

  @EndpointInject(property = "mockToRouterUri")
  private MockEndpoint toRouter;

  @Override
  protected void doPostSetup() throws Exception {
    context.setNameStrategy(new ExplicitCamelContextNameStrategy("Teamwork"));
    teamwork = new Teamwork(uriFactory, context);
  }

  @Override
  public String isMockEndpointsAndSkip() {
    return uriFactory.forDestination(knuth, quoteV1, Destination.ROUTER);
  }

  public String getMockToRouterUri() {
    return "mock://" + uriFactory.forDestination(knuth, quoteV1, Destination.ROUTER);
  }

  @Test
  public void shouldAllowAddingAProducerPluginWhichReceivesMessagesByUserAndTypeAndSendsToRouter() throws Exception {
    ProducerPlugin plugin = new NoopProducerPlugin(Version.v1_0_0(), quoteV1);

    teamwork.addProducerPlugin(knuth, plugin);

    toRouter.expectedBodiesReceived(
        "Beware of bugs in the above code; I have only proved it correct, not tried it.");

    template.sendBody(uriFactory.forDestination(knuth, quoteV1, Destination.PRODUCER),
        "Beware of bugs in the above code; I have only proved it correct, not tried it.");

    toRouter.assertIsSatisfied();
  }
}
