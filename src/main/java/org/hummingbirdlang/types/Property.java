package org.hummingbirdlang.types;

public class Property {
  private final Type type;
  private final String name;

  public Property(Type type, String name) {
    this.type = type;
    this.name = name;
  }
}
