package com.github.alechenninger.teamwork.repositories;

import com.github.alechenninger.teamwork.endpoints.UriFactory;

import java.util.HashMap;
import java.util.Map;

public final class InMemoryUriFactoryRepository implements UriFactoryRepository {
  private final Map<String, UriFactory> uriFactories = new HashMap<>();

  @Override
  public Map<String, UriFactory> allUriFactories() {
    return new HashMap<>(uriFactories);
  }

  @Override
  public UriFactory getUriFactory(String id) {
    return uriFactories.get(id);
  }

  @Override
  public void putUriFactory(String id, UriFactory uriFactory) {
    uriFactories.put(id, uriFactory);
  }
}
