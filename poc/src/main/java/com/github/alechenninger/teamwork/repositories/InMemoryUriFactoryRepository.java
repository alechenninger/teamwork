/*
 * Teamwork integration platform.
 * Copyright (C) 2015  Alec Henninger
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
