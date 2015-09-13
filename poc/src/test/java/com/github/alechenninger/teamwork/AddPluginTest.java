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
import com.github.alechenninger.teamwork.test.ExpressionProducerPlugin;
import com.github.alechenninger.teamwork.test.NoopProducerPlugin;
import com.github.alechenninger.teamwork.test.ProducerPluginSupport;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.DefaultCamelContextNameStrategy;
import org.apache.camel.impl.SimpleRegistry;
import org.apache.camel.model.language.ConstantExpression;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Collections;

@RunWith(JUnit4.class)
public class AddPluginTest extends CamelTestSupport {
  private Teamwork teamwork;

  private UriFactory uriFactory = new PathBasedUriFactory("direct", Collections.<String, String>emptyMap());

  private UserName knuth  = new UserName("knuth");
  private MessageType quoteV1 = new MessageType("Quote", Version.v1_0_0());

  @EndpointInject(property = "mockToRouterUri")
  private MockEndpoint toRouter;

  @Override
  protected void doPostSetup() throws Exception {
    context.setNameStrategy(new DefaultCamelContextNameStrategy("Teamwork"));
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
  public void shouldAddProducerPluginWhichReceivesMessagesByUserAndTypeAndSendsToRouter() throws Exception {
    ProducerPlugin plugin = new NoopProducerPlugin(quoteV1);

    teamwork.addProducerPlugin(knuth, plugin);

    toRouter.expectedBodiesReceived(
        "Beware of bugs in the above code; I have only proved it correct, not tried it.");

    template.sendBody(uriFactory.forDestination(knuth, quoteV1, Destination.PRODUCER),
        "Beware of bugs in the above code; I have only proved it correct, not tried it.");

    toRouter.assertIsSatisfied();
  }

  @Test
  public void shouldReplaceAnAlreadyAddedPluginForSameUserAndMessageType() throws Exception {
    ProducerPlugin noop = new NoopProducerPlugin(quoteV1);
    ProducerPlugin producesConstant = new ExpressionProducerPlugin(quoteV1,
        new ConstantExpression("Premature optimization is the root of all evil."));

    teamwork.addProducerPlugin(knuth, noop);
    teamwork.addProducerPlugin(knuth, producesConstant);

    toRouter.expectedBodiesReceived("Premature optimization is the root of all evil.");

    template.sendBody(uriFactory.forDestination(knuth, quoteV1, Destination.PRODUCER),
        "Beware of bugs in the above code; I have only proved it correct, not tried it.");

    toRouter.assertIsSatisfied();
  }

  @Test
  public void shouldUsePluginProvidedCamelContext() throws Exception {
    ProducerPlugin usesBeanInCustomContext = new ProducerPluginSupport(quoteV1) {
      @Override
      public CamelContext createContext(final String fromUri, final String toUri) throws Exception {
        SimpleRegistry registry = new SimpleRegistry();
        registry.put("bean", new Processor() {
          @Override
          public void process(Exchange exchange) throws Exception {
            exchange.getIn().setBody("Processed by bean");
          }
        });

        CamelContext context = new DefaultCamelContext(registry);

        new RouteBuilder() {
          @Override
          public void configure() throws Exception {
            from(fromUri)
                .beanRef("bean")
                .to(toUri);
          }
        }.addRoutesToCamelContext(context);

        return context;
      }
    };

    teamwork.addProducerPlugin(knuth, usesBeanInCustomContext);

    toRouter.expectedBodiesReceived("Processed by bean");

    template.sendBody(uriFactory.forDestination(knuth, quoteV1, Destination.PRODUCER),
        "The important thing, once you have enough to eat and a nice house, "
            + "is what you can do for others, "
            + "what you can contribute to the enterprise as a whole.");

    toRouter.assertIsSatisfied();
  }
}
