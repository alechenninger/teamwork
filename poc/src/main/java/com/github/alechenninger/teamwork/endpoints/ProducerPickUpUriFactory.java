package com.github.alechenninger.teamwork.endpoints;

/**
 * Defines the entry point for a producer route.
 */
public interface ProducerPickUpUriFactory {
  String forUserAndMessageType(String userName, String messageType);
}
