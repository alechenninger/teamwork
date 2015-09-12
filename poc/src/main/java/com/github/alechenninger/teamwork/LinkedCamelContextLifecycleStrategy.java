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
