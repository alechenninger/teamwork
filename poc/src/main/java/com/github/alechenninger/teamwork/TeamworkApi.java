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

package com.github.alechenninger.teamwork;

import com.github.alechenninger.teamwork.producer.ProducerPlugin;

import org.apache.camel.Predicate;

public interface TeamworkApi {
  void addProducerPlugin(UserName userName, ProducerPlugin plugin) throws Exception;

  Router addRouter(MessageType messageType, Predicate validator) throws Exception;

  Router getRouter(MessageType messageType);
}
