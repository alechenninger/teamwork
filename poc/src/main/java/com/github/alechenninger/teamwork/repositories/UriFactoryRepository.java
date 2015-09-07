package com.github.alechenninger.teamwork.repositories;

import com.github.alechenninger.teamwork.endpoints.UriFactory;

import java.util.Map;

public interface UriFactoryRepository {
  Map<String, UriFactory> allUriFactories();
  UriFactory getUriFactory(String id);
  void putUriFactory(String id, UriFactory uri);
}
