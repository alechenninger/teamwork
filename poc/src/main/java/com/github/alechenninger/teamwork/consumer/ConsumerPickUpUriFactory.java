package com.github.alechenninger.teamwork.consumer;

public interface ConsumerPickUpUriFactory {
  String forUserAndMessageType(String userName, String messageType);
}
