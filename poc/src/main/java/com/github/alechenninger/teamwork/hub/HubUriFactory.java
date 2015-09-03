package com.github.alechenninger.teamwork.hub;

public interface HubUriFactory {
  String forUserAndMessageType(String userName, String messageType);
}
