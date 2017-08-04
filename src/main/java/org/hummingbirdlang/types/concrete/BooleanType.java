package org.hummingbirdlang.types.concrete;

import org.hummingbirdlang.types.ConcreteType;
import org.hummingbirdlang.types.Property;
import org.hummingbirdlang.types.PropertyNotFoundException;

public final class BooleanType extends ConcreteType {
  public static final BooleanType SINGLETON = new BooleanType();

  private BooleanType() {
  }

  @Override
  public Property getProperty(String name) throws PropertyNotFoundException {
    throw new PropertyNotFoundException("Not yet implemented");
  }
}
