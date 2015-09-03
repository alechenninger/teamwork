package com.github.alechenninger.teamwork.consumer;

public interface ConsumerDeliveryUriFactory {
  String forUserAndMessageType(String userName, String messageType);
}
