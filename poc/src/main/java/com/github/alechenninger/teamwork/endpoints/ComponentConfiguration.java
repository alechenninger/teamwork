package com.github.alechenninger.teamwork.endpoints;

import org.apache.camel.CamelContext;

/**
 * The boundaries of your producers/consumers can be anything Camel supports. Particularly for
 * consumers, you will probably want to send to some endpoint (whether queue or HTTP or whatever)
 * after you consume a message. This means we want to be able to serialize / deserialize Camel
 * component configuration which can be used by particular users.
 */
public interface ComponentConfiguration {
  void addToCamelContext(CamelContext context) throws Exception;
}
