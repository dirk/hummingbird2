package org.hummingbirdlang.types.composite;

import org.hummingbirdlang.types.CompositeType;
import org.hummingbirdlang.types.Property;
import org.hummingbirdlang.types.PropertyNotFoundException;

public class AnyType extends CompositeType {
  public AnyType() {
  }

  @Override
  public Property getProperty(String name) throws PropertyNotFoundException {
    throw new PropertyNotFoundException("Not yet implemented");
  }
}
