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
import java.io.Serializable;
import java.util.Objects;

/**
 * Value type for a validated user name.
 */
public final class UserName implements Serializable, Comparable<String>, CharSequence {
  private static final long serialVersionUID = 1L;

  private final String userName;

  public UserName(@Nonnull String userName) {
    // TODO: validation... what is valid in a URI? utf8?

    this.userName = Objects.requireNonNull(userName, "userName");
  }

  @Override
  public int length() {
    return userName.length();
  }

  @Override
  public char charAt(int index) {
    return userName.charAt(index);
  }

  @Override
  public CharSequence subSequence(int start, int end) {
    return userName.subSequence(start, end);
  }

  @Override
  @Nonnull
  public String toString() {
    return userName;
  }

  @Override
  public int compareTo(@Nonnull String o) {
    return userName.compareTo(o);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserName userName1 = (UserName) o;
    return Objects.equals(userName, userName1.userName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userName);
  }
}
