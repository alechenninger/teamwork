package com.github.alechenninger.teamwork.endpoints;

import java.util.Map;

public interface UriFactoryRepository {
  Map<String, UriFactory> allUriFactories();
  UriFactory getUriFactory(String id);
  void putUriFactory(String id, UriFactory uri);
}
