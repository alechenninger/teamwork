package com.github.alechenninger.teamwork;

import static com.google.common.base.Preconditions.checkArgument;

import com.google.common.base.Joiner;

import java.util.Objects;
import java.util.StringTokenizer;

public final class Version implements Comparable<Version> {
  private final int major;
  private final int minor;
  private final int patch;

  public Version(int major, int minor, int patch) {
    this.major = major;
    this.minor = minor;
    this.patch = patch;
  }

  public static Version parse(String version) {
    StringTokenizer tokenizer = new StringTokenizer(version, ".", false);

    checkArgument(tokenizer.countTokens() == 3,
        "Expected exactly 3 version numbers delimited by . but got: " + version);

    int major = Integer.parseInt(tokenizer.nextToken());
    int minor = Integer.parseInt(tokenizer.nextToken());
    int patch = Integer.parseInt(tokenizer.nextToken());

    return new Version(major, minor, patch);
  }

  public static Version v1_0_0() {
    return new Version(1, 0, 0);
  }

  public int major() {
    return major;
  }

  public int minor() {
    return minor;
  }

  public int patch() {
    return patch;
  }

  @Override
  public int compareTo(Version o) {
    int majorDiff = major() - o.major();
    if (majorDiff != 0) return majorDiff;

    int minorDiff = minor() - o.minor();
    if (minorDiff != 0) return minorDiff;

    int patchDiff = patch() - o.patch();
    if (patchDiff != 0) return patchDiff;

    return 0;
  }

  @Override
  public String toString() {
    return Joiner.on('.').join(major, minor, patch);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Version version = (Version) o;
    return Objects.equals(major, version.major) &&
        Objects.equals(minor, version.minor) &&
        Objects.equals(patch, version.patch);
  }

  @Override
  public int hashCode() {
    return Objects.hash(major, minor, patch);
  }
}
