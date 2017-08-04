package org.hummingbirdlang.types;

public class PropertyNotFoundException extends TypeException {
  public PropertyNotFoundException(String message) {
    super(message);
  }

  public PropertyNotFoundException(Type type, String name) {
    super("Unknown property " + name + " of " + type.toString());
  }
}
