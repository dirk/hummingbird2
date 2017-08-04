package org.hummingbirdlang.types.concrete;

import org.hummingbirdlang.types.ConcreteType;
import org.hummingbirdlang.types.Property;
import org.hummingbirdlang.types.PropertyNotFoundException;

public final class NullType extends ConcreteType {
  public static final NullType SINGLETON = new NullType();

  private NullType() {
  }

  @Override
  public Property getProperty(String name) throws PropertyNotFoundException {
    throw new PropertyNotFoundException("Not yet implemented");
  }
}
