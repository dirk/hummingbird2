package org.hummingbirdlang.types.concrete;

import org.hummingbirdlang.types.ConcreteType;

public final class IntegerType extends ConcreteType {
  public static final IntegerType SINGLETON = new IntegerType();

  private IntegerType() {
  }
}
