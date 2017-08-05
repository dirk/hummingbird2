package org.hummingbirdlang.types;

import com.oracle.truffle.api.CallTarget;

public final class FunctionType extends ConcreteType {
  private final Type[] argumentTypes;
  private final Type returnType;
  private final String name;
  private final CallTarget callTarget;

  public FunctionType(
    Type[] argumentTypes,
    Type returnType,
    String name,
    CallTarget callTarget
  ) {
    this.argumentTypes = argumentTypes;
    this.returnType = returnType;
    this.name = name;
    this.callTarget = callTarget;
  }

  public String getName() {
    return this.name;
  }

  public CallTarget getCallTarget() {
    return this.callTarget;
  }

  @Override
  public Property getProperty(String name) throws PropertyNotFoundException {
    throw new PropertyNotFoundException("Not yet implemented");
  }
}
