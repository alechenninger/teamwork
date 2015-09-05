package com.github.alechenninger.teamwork.producer;

public interface ProducerFactory {
  ProducerPlugin createProducer(String fromUri, String toUri);
}
