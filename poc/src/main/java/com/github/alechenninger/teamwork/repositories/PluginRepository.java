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

import java.util.Set;

public interface PluginRepository {
  void addProducerPlugin(UserName userName, ProducerPlugin plugin);

  void addConsumerPlugin(UserName userName, ConsumerPlugin plugin);

  // TODO: May want to return different data structure for these; perhaps map or table
  Set<ProducerPlugin> getProducerPluginsForUser(UserName userName);

  Set<ConsumerPlugin> getConsumerPluginsForUser(UserName userName);

  ProducerPlugin getProducerPlugin(UserName userName, MessageType messageType, Version version);

  ConsumerPlugin getConsumerPlugin(UserName userName, MessageType messageType, Version version);
}
