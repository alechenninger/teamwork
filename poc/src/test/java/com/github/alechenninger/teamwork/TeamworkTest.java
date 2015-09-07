package com.github.alechenninger.teamwork;

import com.github.alechenninger.teamwork.endpoints.PathBasedUriFactory;
import com.github.alechenninger.teamwork.endpoints.UriFactory;
import com.github.alechenninger.teamwork.producer.ProducerPlugin;
import com.github.alechenninger.teamwork.repositories.InMemoryPluginRepository;
import com.github.alechenninger.teamwork.repositories.PluginRepository;
import com.github.alechenninger.teamwork.test.NoopProducerPlugin;

import org.apache.camel.EndpointInject;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Collections;

@RunWith(JUnit4.class)
public class TeamworkTest extends CamelTestSupport {
  private PluginRepository pluginRepository = new InMemoryPluginRepository();
  private UriFactory directUriFactory = new PathBasedUriFactory("direct", Collections.<String, String>emptyMap());
  private UriFactory mockUriFactory = new PathBasedUriFactory("mock", Collections.<String, String>emptyMap());

  private Teamwork teamwork;
  private UserName knuth  = new UserName("knuth");
  private MessageType QuoteV1 = new MessageType("Quote", Version.v1_0_0());

  @EndpointInject(property = "mockToRouterUri")
  private MockEndpoint toRouter;

  @Override
  protected void doPostSetup() throws Exception {
    teamwork = new Teamwork(directUriFactory, context);
  }

  @Test
  public void shouldAllowAddingAProducerPluginWhichReceivesMessagesByUserAndTypeAndSendsToRouter() throws Exception {
    ProducerPlugin plugin = new NoopProducerPlugin(Version.v1_0_0(), QuoteV1);

    teamwork.addProducerPlugin(knuth, plugin);

    toRouter.expectedBodiesReceived(
        "Beware of bugs in the above code; I have only proved it correct, not tried it.");

    template.sendBody(directUriFactory.forDestination(knuth, QuoteV1, Destination.PRODUCER),
        "Beware of bugs in the above code; I have only proved it correct, not tried it.");

    toRouter.assertIsSatisfied();
  }

  @Override
  public String isMockEndpointsAndSkip() {
    return directUriFactory.forDestination(knuth, QuoteV1, Destination.ROUTER);
  }

  public String getMockToRouterUri() {
    return "mock://" + directUriFactory.forDestination(knuth, QuoteV1, Destination.ROUTER);
  }
}
