package org.hummingbirdlang.types.composite;

import org.hummingbirdlang.types.CompositeType;
import org.hummingbirdlang.types.Property;
import org.hummingbirdlang.types.PropertyNotFoundException;
import org.hummingbirdlang.types.Type;

/**
 * When we describe a variable like `foo = "bar"` the type of `foo` is
 * `String`, but to describe types themselves we need an intermediate
 * container to hold the reference to that underlying type. That's
 * the function of the type reference type.
 */
public class TypeReferenceType extends CompositeType {
  private Type type;

  public TypeReferenceType(Type type) {
    this.type = type;
  }

  public Type getType() {
    return this.type;
  }

  @Override
  public Property getProperty(String name) throws PropertyNotFoundException {
    throw new PropertyNotFoundException("Not yet implemented");
  }

  @Override
  public String toString() {
    return "'" + this.type.toString();
  }
}
