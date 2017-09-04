package org.hummingbirdlang.types.composite;

import org.hummingbirdlang.types.CompositeType;
import org.hummingbirdlang.types.Property;
import org.hummingbirdlang.types.PropertyNotFoundException;
import org.hummingbirdlang.types.Type;

/**
 * Disjoint union type (aka. sum type): it is composed of members A and B.
 * Eventually the union is discriminated into either A or B.
 */
public class SumType extends CompositeType {
  public final Type[] subTypes;

  public SumType(Type[] subTypes) {
    this.subTypes = subTypes;
  }

  @Override
  public Property getProperty(String name) throws PropertyNotFoundException {
    throw new PropertyNotFoundException("Not yet implemented");
  }

  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();
    boolean bar = false;
    for (Type subType : this.subTypes) {
      if (bar) {
        result.append(" | ");
      } else {
        bar = true;
      }
      result.append(subType.toString());
    }
    return result.toString();
  }
}
