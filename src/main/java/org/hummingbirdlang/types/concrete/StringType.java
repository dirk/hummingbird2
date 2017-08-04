package org.hummingbirdlang.types.concrete;

import org.hummingbirdlang.types.ConcreteType;
import org.hummingbirdlang.types.Property;
import org.hummingbirdlang.types.PropertyNotFoundException;

public final class StringType extends ConcreteType {
  public static final StringType SINGLETON = new StringType();

  private StringType() {
  }

  @Override
  public Property getProperty(String name) throws PropertyNotFoundException {
    throw new PropertyNotFoundException(this, name);
  }

  @Override
  public String toString() {
    // NOTE: Concrete types' string representations are prefixed with "$"
    //   to indicate their internal, concrete nature (for now).
    return "$String";
  }
}
