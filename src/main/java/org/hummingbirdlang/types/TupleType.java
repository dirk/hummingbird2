package org.hummingbirdlang.types;

public class TupleType extends CompositeType {
  private Type[] elements;

  @Override
  public Property getProperty(String name) throws PropertyNotFoundException {
    throw new PropertyNotFoundException("Not yet implemented");
  }
}
