package com.github.alechenninger.teamwork.repositories;

import com.github.alechenninger.teamwork.MessageType;
import com.github.alechenninger.teamwork.UserName;

import org.apache.camel.Predicate;

public interface FilterRepository {
  // TODO: Should these be versioned and "installed" then "activated" like plugins?
  Predicate getConsumerFilter(UserName userName, MessageType messageType);
  Predicate getProducerFilter(UserName userName, MessageType messageType);
}
