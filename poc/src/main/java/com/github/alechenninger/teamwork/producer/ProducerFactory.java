package com.github.alechenninger.teamwork.producer;

public interface ProducerFactory {
  MessageProducer createProducer(String fromUri, String toUri);
}
