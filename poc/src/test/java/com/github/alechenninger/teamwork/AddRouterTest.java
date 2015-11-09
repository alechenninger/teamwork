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

import static org.hamcrest.CoreMatchers.instanceOf;

import com.github.alechenninger.teamwork.endpoints.PathBasedUriFactory;
import com.github.alechenninger.teamwork.endpoints.UriFactory;
import com.github.alechenninger.teamwork.router.CanonicalTopicUriFactory;
import com.github.alechenninger.teamwork.router.DirectCanonicalTopicUriFactory;

import org.apache.camel.CamelExecutionException;
import org.apache.camel.EndpointInject;
import org.apache.camel.builder.PredicateBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.impl.DefaultCamelContextNameStrategy;
import org.apache.camel.processor.validation.PredicateValidationException;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Collections;

@RunWith(JUnit4.class)
public class AddRouterTest extends CamelTestSupport {
  private TeamworkApi teamwork;

  private UriFactory uriFactory = new PathBasedUriFactory("direct", Collections.<String, String>emptyMap());

  private UserName knuth  = new UserName("knuth");
  private UserName dijkstra = new UserName("dijkstra");

  private MessageType quoteV1 = new MessageType("Quote", Version.v1_0_0());

  @EndpointInject(property = "mockToConsumerUri")
  private MockEndpoint toConsumer;

  @Override
  protected void doPostSetup() throws Exception {
    context.setNameStrategy(new DefaultCamelContextNameStrategy("Teamwork"));
    teamwork = new Teamwork(uriFactory, context);
  }

  @Override
  public String isMockEndpointsAndSkip() {
    return uriFactory.forDestination(dijkstra, quoteV1, Destination.CONSUMER);
  }

  public String getMockToConsumerUri() {
    return "mock://" + uriFactory.forDestination(dijkstra, quoteV1, Destination.CONSUMER);
  }

  @Test
  public void shouldRouteProducedMessagesForMessageTypeToAllowedConsumers() throws Exception {
    teamwork.addRouter(quoteV1, PredicateBuilder.constant(true));

    teamwork.filterProducer(knuth, quoteV1, PredicateBuilder.constant(true));
    teamwork.filterConsumer(dijkstra, quoteV1, PredicateBuilder.constant(true));

    toConsumer.expectedBodiesReceived("I can’t be as confident about computer science as I can "
        + "about biology. Biology easily has 500 years of exciting problems to work on. It’s at "
        + "that level.");

    template.sendBody(uriFactory.forDestination(knuth, quoteV1, Destination.ROUTER),
        "I can’t be as confident about computer science as I can about biology. "
        + "Biology easily has 500 years of exciting problems to work on. It’s at that level.");

    toConsumer.assertIsSatisfied();
  }

  @Test
  public void shouldFilterOutProducedMessagesNotAllowedToRouter() throws Exception {
    teamwork.addRouter(quoteV1, PredicateBuilder.constant(true));

    teamwork.filterProducer(knuth, quoteV1, PredicateBuilder.constant(false));
    teamwork.filterConsumer(dijkstra, quoteV1, PredicateBuilder.constant(true));

    toConsumer.expectedMessageCount(0);

    template.sendBody(uriFactory.forDestination(knuth, quoteV1, Destination.ROUTER),
        "I can’t be as confident about computer science as I can about biology. "
        + "Biology easily has 500 years of exciting problems to work on. It’s at that level.");

    toConsumer.assertIsSatisfied();
  }

  @Test
  public void shouldNotRouteInvalidMessagesToConsumers() throws Exception {
    teamwork.addRouter(quoteV1, PredicateBuilder.constant(false));

    teamwork.filterProducer(knuth, quoteV1, PredicateBuilder.constant(true));
    teamwork.filterConsumer(dijkstra, quoteV1, PredicateBuilder.constant(true));

    toConsumer.expectedMessageCount(0);

    try {
      template.sendBody(uriFactory.forDestination(knuth, quoteV1, Destination.ROUTER),
          "I can’t be as confident about computer science as I can about biology. "
          + "Biology easily has 500 years of exciting problems to work on. It’s at that level.");
      fail("Expected PredicateValidationException");
    } catch (CamelExecutionException e) {
      assertThat(e.getCause(), instanceOf(PredicateValidationException.class));
    }

    toConsumer.assertIsSatisfied();
  }

  @Test
  public void shouldNotRouteValidMessagesToFilteredOutConsumers() throws Exception {
    teamwork.addRouter(quoteV1, PredicateBuilder.constant(true));

    teamwork.filterProducer(knuth, quoteV1, PredicateBuilder.constant(true));
    teamwork.filterConsumer(dijkstra, quoteV1, PredicateBuilder.constant(false));

    toConsumer.expectedMessageCount(0);

    template.sendBody(uriFactory.forDestination(knuth, quoteV1, Destination.ROUTER),
        "I can’t be as confident about computer science as I can about biology. "
        + "Biology easily has 500 years of exciting problems to work on. It’s at that level.");

    toConsumer.assertIsSatisfied();
  }
}
