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
  public String toString() {
    return userName;
  }

  @Override
  public int compareTo(String o) {
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
