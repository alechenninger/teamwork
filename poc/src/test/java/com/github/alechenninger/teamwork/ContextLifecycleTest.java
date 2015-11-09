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

import static org.junit.Assert.assertEquals;

import com.github.alechenninger.teamwork.endpoints.PathBasedUriFactory;
import com.github.alechenninger.teamwork.endpoints.UriFactory;
import com.github.alechenninger.teamwork.producer.ProducerPlugin;
import com.github.alechenninger.teamwork.test.ProducerPluginSupport;

import org.apache.camel.CamelContext;
import org.apache.camel.ServiceStatus;
import org.apache.camel.impl.DefaultCamelContext;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Collections;

@RunWith(JUnit4.class)
public class ContextLifecycleTest {
  private UriFactory uriFactory = new PathBasedUriFactory("direct", Collections.<String, String>emptyMap());
  private CamelContext teamworkContext = new DefaultCamelContext();
  private final CamelContext pluginContext = new DefaultCamelContext();

  private UserName userName = new UserName("user");
  private MessageType messageType = new MessageType("messageType", Version.v1_0_0());

  private TeamworkApi teamwork = new Teamwork(uriFactory, teamworkContext);

  private ProducerPlugin plugin = new ProducerPluginSupport(messageType) {
    @Override
    public CamelContext createContext(String fromUri, String toUri) throws Exception {
      return pluginContext;
    }
  };

  @After
  public void stopTeamworkContext() throws Exception {
    teamworkContext.stop();
  }

  @After
  public void stopPluginContext() throws Exception {
    pluginContext.stop();
  }

  @Test
  public void shouldStartPluginContextsWhenTeamworkContextIsStarted() throws Exception {
    teamwork.addProducerPlugin(userName, plugin);

    teamworkContext.start();

    assertEquals(ServiceStatus.Started, pluginContext.getStatus());
  }

  @Test
  public void shouldNotStartPluginContextIfTeamworkContextIsNotStarted() throws Exception {
    teamwork.addProducerPlugin(userName, plugin);

    assertEquals(ServiceStatus.Stopped, pluginContext.getStatus());
  }

  @Test
  public void shouldStartPluginContextWhenAddedIfTeamworkContextIsAlreadyStarted() throws Exception {
    teamworkContext.start();

    teamwork.addProducerPlugin(userName, plugin);

    assertEquals(ServiceStatus.Started, pluginContext.getStatus());
  }

  @Test
  public void shouldStopPluginContextWhenTeamworkContextIsStopped() throws Exception {
    teamworkContext.start();

    teamwork.addProducerPlugin(userName, plugin);

    teamworkContext.stop();

    assertEquals(ServiceStatus.Stopped, pluginContext.getStatus());
  }
}
