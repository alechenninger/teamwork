package com.github.alechenninger.teamwork.endpoints;

import com.github.alechenninger.teamwork.MessageType;
import com.github.alechenninger.teamwork.UserName;

public interface ConsumerPickUpUriFactory {
  String forUserAndMessageType(UserName userName, MessageType messageType);
}
