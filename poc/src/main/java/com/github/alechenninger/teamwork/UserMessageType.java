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

import java.util.Objects;

final class UserMessageType {
  final UserName userName;
  final MessageType messageType;

  UserMessageType(UserName userName, MessageType messageType) {
    this.userName = userName;
    this.messageType = messageType;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserMessageType that = (UserMessageType) o;
    return Objects.equals(userName, that.userName) &&
        Objects.equals(messageType, that.messageType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userName, messageType);
  }

  @Override
  public String toString() {
    return "UserMessageType{" +
        "userName=" + userName +
        ", messageType=" + messageType +
        '}';
  }
}
