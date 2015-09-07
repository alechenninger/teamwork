package com.github.alechenninger.teamwork.repositories;

import com.github.alechenninger.teamwork.MessageType;

import org.apache.camel.Predicate;

import java.util.List;
import java.util.Map;

public interface MessageTypeRepository {
  List<MessageType> allTypes();
  Predicate getValidator(MessageType messageType);
  Map<MessageType, Predicate> allTypesWithValidators();
}
