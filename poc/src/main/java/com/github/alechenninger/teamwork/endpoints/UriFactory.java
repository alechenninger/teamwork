package com.github.alechenninger.teamwork.endpoints;

import com.github.alechenninger.teamwork.MessageType;
import com.github.alechenninger.teamwork.UserName;

public interface UriFactory {
  String forDestination(UserName userName, MessageType messageType, Destination destination);
}
