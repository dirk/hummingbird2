package org.hummingbirdlang.types.composite;

import org.hummingbirdlang.types.CompositeType;
import org.hummingbirdlang.types.Type;

/**
 * Disjoint union type (aka. sum type): it is composed of members A and B.
 * Eventually the union is discriminated into either A or B.
 */
public class SumType extends CompositeType {
  public final Type aType;
  public final Type bType;

  public SumType(Type aType, Type bType) {
    this.aType = aType;
    this.bType = bType;
  }
}
