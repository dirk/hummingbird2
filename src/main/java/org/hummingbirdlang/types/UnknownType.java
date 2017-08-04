package org.hummingbirdlang.types;

public class UnknownType extends Type {
  @Override
  public Property getProperty(String name) throws PropertyNotFoundException {
    throw new PropertyNotFoundException("Unknown type cannot have property: " + name);
  }
}
