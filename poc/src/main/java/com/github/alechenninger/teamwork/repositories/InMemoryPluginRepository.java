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

import com.github.alechenninger.teamwork.MessageType;
import com.github.alechenninger.teamwork.UserName;
import com.github.alechenninger.teamwork.Version;
import com.github.alechenninger.teamwork.consumer.ConsumerPlugin;
import com.github.alechenninger.teamwork.producer.ProducerPlugin;

import com.google.common.base.Predicate;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import com.google.common.collect.Table;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class InMemoryPluginRepository implements PluginRepository {
  private final Table<UserName, TypeAndVersion, ProducerPlugin> producerPlugins = HashBasedTable.create();
  private final Table<UserName, TypeAndVersion, ConsumerPlugin> consumerPlugins = HashBasedTable.create();

  @Override
  public void addProducerPlugin(UserName userName, ProducerPlugin plugin) {
    producerPlugins.put(userName, TypeAndVersion.fromPlugin(plugin), plugin);
  }

  @Override
  public void addConsumerPlugin(UserName userName, ConsumerPlugin plugin) {
    consumerPlugins.put(userName, TypeAndVersion.fromPlugin(plugin), plugin);
  }

  @Override
  public Set<ProducerPlugin> getProducerPluginsForUser(UserName userName) {
    return new HashSet<>(producerPlugins.row(userName).values());
  }

  @Override
  public Set<ConsumerPlugin> getConsumerPluginsForUser(UserName userName) {
    return new HashSet<>(consumerPlugins.row(userName).values());
  }

  @Override
  public ProducerPlugin getProducerPlugin(UserName userName, final MessageType messageType, Version
      version) {
    return producerPlugins.get(userName, new TypeAndVersion(messageType, version));
  }

  @Override
  public ConsumerPlugin getConsumerPlugin(UserName userName, MessageType messageType, Version version) {
    return consumerPlugins.get(userName, new TypeAndVersion(messageType, version));
  }

  static class TypeAndVersion {
    final MessageType type;
    final Version version;

    static TypeAndVersion fromPlugin(ProducerPlugin plugin) {
      return new TypeAndVersion(plugin.messageType(), plugin.version());
    }

    static TypeAndVersion fromPlugin(ConsumerPlugin plugin) {
      return new TypeAndVersion(plugin.messageType(), plugin.version());
    }

    TypeAndVersion(MessageType type, Version version) {
      this.type = type;
      this.version = version;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      TypeAndVersion that = (TypeAndVersion) o;
      return Objects.equals(type, that.type) &&
          Objects.equals(version, that.version);
    }

    @Override
    public int hashCode() {
      return Objects.hash(type, version);
    }
  }
}
