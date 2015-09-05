package com.github.alechenninger.teamwork;

import com.github.alechenninger.teamwork.endpoints.ActiveMqConfiguration;
import com.github.alechenninger.teamwork.endpoints.ComponentConfiguration;
import com.github.alechenninger.teamwork.endpoints.PathBasedUriFactory;
import com.github.alechenninger.teamwork.endpoints.UriFactory;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

import java.util.HashMap;

public class Teamwork {
  // For now, start here.
  public static void main(String[] args) throws Exception {
    CamelContext context = new DefaultCamelContext();
    ComponentConfiguration activemqComponentConfig =
        new ActiveMqConfiguration(null, null, "vm://localhost", "activemq", false, null, null, null, false);
    activemqComponentConfig.addToCamelContext(context);

    UriFactory activeMqUriFactory = new PathBasedUriFactory("activemq", new HashMap<String, String>());

  }
}
