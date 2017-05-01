package org.hummingbirdlang.types.concrete;

import org.hummingbirdlang.types.ConcreteType;

public final class BooleanType extends ConcreteType {
  public static final BooleanType SINGLETON_TRUE = new BooleanType(true);
  public static final BooleanType SINGLETON_FALSE = new BooleanType(false);

  public final boolean value;

  private BooleanType(boolean value) {
    this.value = value;
  }
}
