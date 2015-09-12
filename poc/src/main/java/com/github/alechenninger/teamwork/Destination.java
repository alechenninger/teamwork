package com.github.alechenninger.teamwork;

/**
 * The four core destinations a message is persisted. Each is named after where the message is being
 * sent <em>to</em>.
 */
public enum Destination {
  PRODUCER("producer"),
  ROUTER("router"),
  CONSUMER("consumer"),
  APPLICATION("application"); // TODO: Better name?

  private final String name;

  Destination(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return name;
  }
}