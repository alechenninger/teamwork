package com.github.alechenninger.teamwork.endpoints;

public interface ConsumerPickUpUriFactory {
  String forUserAndMessageType(String userName, String messageType);
}
