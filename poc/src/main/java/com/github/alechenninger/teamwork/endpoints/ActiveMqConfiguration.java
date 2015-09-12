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

package com.github.alechenninger.teamwork.endpoints;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSslConnectionFactory;
import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.apache.camel.CamelContext;

import javax.jms.ConnectionFactory;
import java.util.Collections;

/**
 * Serializable document describing an active mq client component config.
 */
public class ActiveMqConfiguration implements ComponentConfiguration {
  private final String userName;
  private final String password;
  private final String brokerUrl;
  private final String componentName;
  private final boolean useSsl;
  private final String trustStorePassword;
  private final String trustStore;
  private final Integer maxConnections;
  private final boolean useConnectionPool;

  public ActiveMqConfiguration(String userName, String password, String brokerUrl, String componentName,
      boolean useSsl, String trustStorePassword, String trustStore, Integer maxConnections,
      boolean useConnectionPool) {
    this.userName = userName;
    this.password = password;
    this.brokerUrl = brokerUrl;
    this.componentName = componentName;
    this.useSsl = useSsl;
    this.trustStorePassword = trustStorePassword;
    this.trustStore = trustStore;
    this.maxConnections = maxConnections;
    this.useConnectionPool = useConnectionPool;
  }

  @Override
  public void addToCamelContext(CamelContext context) throws Exception {
    if (context.hasComponent(componentName()) != null) {
      throw new IllegalStateException("Component already added to CamelContext.");
    }

    ActiveMQComponent component = new ActiveMQComponent();
    ConnectionFactory connectionFactory = getConnectionFactory();
    component.setConnectionFactory(connectionFactory);
    context.addComponent(componentName(), component);
  }

  public String userName() {
    return userName;
  }

  public String password() {
    return password;
  }

  public String brokerUrl() {
    return brokerUrl;
  }

  public String componentName() {
    return componentName;
  }

  public boolean useSsl() {
    return useSsl;
  }

  public String trustStorePassword() {
    return trustStorePassword;
  }

  public String trustStore() {
    return trustStore;
  }

  public boolean useConnectionPool() {
    return useConnectionPool;
  }

  public Integer maxConnections() {
    return maxConnections;
  }

  private ConnectionFactory getConnectionFactory() throws Exception {
    final ActiveMQConnectionFactory connectionFactory;

    if (useSsl()) {
      ActiveMQSslConnectionFactory sslConnectionFactory = new ActiveMQSslConnectionFactory(brokerUrl());
      sslConnectionFactory.setTrustStore(trustStore());
      sslConnectionFactory.setTrustStorePassword(trustStorePassword());
      connectionFactory = sslConnectionFactory;
    } else {
      connectionFactory = new ActiveMQConnectionFactory(brokerUrl());
    }
    connectionFactory.setUserName(userName());
    connectionFactory.setPassword(password());

    if (useConnectionPool()) {
      PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory(connectionFactory);
      pooledConnectionFactory.setMaxConnections(maxConnections());
      return pooledConnectionFactory;
    }

    return connectionFactory;
  }
}
