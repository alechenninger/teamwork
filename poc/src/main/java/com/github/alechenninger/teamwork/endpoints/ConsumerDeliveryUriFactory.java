package com.github.alechenninger.teamwork.endpoints;

public interface ConsumerDeliveryUriFactory {
  String forUserAndMessageType(String userName, String messageType);
}
