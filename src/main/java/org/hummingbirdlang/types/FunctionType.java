package org.hummingbirdlang.types;

import org.hummingbirdlang.types.scope.Scope;

import com.oracle.truffle.api.CallTarget;

public final class FunctionType extends ConcreteType {
  private final Type[] argumentTypes;
  private final Type returnType;
  private final String name;
  private final CallTarget callTarget;
  private Scope scope;

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

  public Scope getScope() {
    if (this.scope != null && !this.scope.isClosed()) {
      throw new RuntimeException("Scope not yet closed");
    }
    return this.scope;
  }

  public void setScope(Scope scope) {
    if (this.scope != null) {
      throw new RuntimeException("Cannot re-set scope");
    }
    this.scope = scope;
  }

  @Override
  public Property getProperty(String name) throws PropertyNotFoundException {
    throw new PropertyNotFoundException("Not yet implemented");
  }
}
