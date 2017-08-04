package org.hummingbirdlang.types.concrete;

import org.hummingbirdlang.types.ConcreteType;
import org.hummingbirdlang.types.Property;
import org.hummingbirdlang.types.PropertyNotFoundException;

public final class IntegerType extends ConcreteType {
  public static final IntegerType SINGLETON = new IntegerType();

  private IntegerType() {
  }

  @Override
  public Property getProperty(String name) throws PropertyNotFoundException {
    throw new PropertyNotFoundException("Not yet implemented");
  }
}
