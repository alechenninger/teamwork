package com.github.alechenninger.teamwork;

import org.apache.camel.CamelContext;

public interface ContextRepository {
  CamelContext getProducerContextForUser(String userName);
  CamelContext getConsumerContextForUser(String userName);
}
