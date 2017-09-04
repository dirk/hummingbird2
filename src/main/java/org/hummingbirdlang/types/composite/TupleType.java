package org.hummingbirdlang.types.composite;

import org.hummingbirdlang.types.CompositeType;
import org.hummingbirdlang.types.Property;
import org.hummingbirdlang.types.PropertyNotFoundException;
import org.hummingbirdlang.types.Type;

public class TupleType extends CompositeType {
  private Type[] elements;

  public TupleType(Type[] elements) {
    this.elements = elements;
  }

  public static TupleType unit() {
    return new TupleType(new Type[0]);
  }

  @Override
  public Property getProperty(String name) throws PropertyNotFoundException {
    throw new PropertyNotFoundException("Not yet implemented");
  }
}
