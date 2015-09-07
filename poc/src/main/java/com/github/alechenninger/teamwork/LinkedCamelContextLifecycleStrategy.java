package com.github.alechenninger.teamwork;

import org.apache.camel.CamelContext;
import org.apache.camel.VetoCamelContextStartException;
import org.apache.camel.support.LifecycleStrategySupport;

import java.util.Collection;

/**
 * Starts and stops "linked" contexts when the observed context is started or stopped.
 */
public class LinkedCamelContextLifecycleStrategy extends LifecycleStrategySupport {
  private final Iterable<CamelContext> linkedContexts;

  /**
   * @param linkedContexts Deliberately <em>not</em> copied. Updates to this iterable will pass
   * through to this class.
   */
  public LinkedCamelContextLifecycleStrategy(Iterable<CamelContext> linkedContexts) {
    this.linkedContexts = linkedContexts;
  }

  @Override
  public void onContextStart(CamelContext context) throws VetoCamelContextStartException {
    for (CamelContext linkedContext : linkedContexts) {
      try {
        linkedContext.start();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public void onContextStop(CamelContext context) {
    for (CamelContext linkedContext : linkedContexts) {
      try {
        linkedContext.stop();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
