package com.github.alechenninger.teamwork.endpoints;

public interface HubUriFactory {
  String forUserAndMessageType(String userName, String messageType);
}
