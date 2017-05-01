package org.hummingbirdlang.types.concrete;

import org.hummingbirdlang.types.ConcreteType;

public final class NullType extends ConcreteType {
  public static final NullType SINGLETON = new NullType();

  private NullType() {
  }
}
