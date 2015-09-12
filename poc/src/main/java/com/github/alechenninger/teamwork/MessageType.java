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
