package com.github.alechenninger.teamwork.endpoints;

import java.util.Map;

public interface UriRepository {
  Map<String, String> allUris();
  String getUri(String id);
  void addUri(String id, String uri);
}
