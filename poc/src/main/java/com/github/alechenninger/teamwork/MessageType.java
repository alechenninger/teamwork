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

import javax.annotation.Nonnull;
import java.util.Objects;

public final class MessageType {
  private final String name;
  private final Version version;

  public MessageType(@Nonnull String name, @Nonnull Version version) {
    this.name = Objects.requireNonNull(name, "name");
    this.version = Objects.requireNonNull(version, "version");
  }

  public String name() {
    return name;
  }

  public Version version() {
    return version;
  }

  @Override
  public String toString() {
    return name + "-v" + version;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MessageType that = (MessageType) o;
    return Objects.equals(name, that.name) &&
        Objects.equals(version, that.version);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, version);
  }
}
