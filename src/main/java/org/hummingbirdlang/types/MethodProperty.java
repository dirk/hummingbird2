package org.hummingbirdlang.types;

import org.hummingbirdlang.types.concrete.MethodType;

public class MethodProperty extends Property {
  private final MethodType methodType;

  public MethodProperty(Type parent, String name, MethodType methodType) {
    super(parent, name);
    this.methodType = methodType;
  }

  public static MethodProperty fromType(MethodType methodType) {
    return new MethodProperty(methodType.getParent(), methodType.getName(), methodType);
  }

  @Override
  public Type getType() {
    return this.methodType;
  }
}
