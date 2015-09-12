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

/**
 * The four core destinations a message is persisted. Each is named after where the message is being
 * sent <em>to</em>.
 */
public enum Destination {
  PRODUCER("producer"),
  ROUTER("router"),
  CONSUMER("consumer"),
  APPLICATION("application"); // TODO: Better name?

  private final String name;

  Destination(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return name;
  }
}
