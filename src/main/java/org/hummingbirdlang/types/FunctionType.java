package org.hummingbirdlang.types;

import org.hummingbirdlang.types.scope.Scope;

import com.oracle.truffle.api.CallTarget;

public final class FunctionType extends ConcreteType {
  private final Type[] parameterTypes;
  private final Type returnType;
  private final String name;
  private final CallTarget callTarget;
  // Scope where the function was declared.
  private Scope declarationScope;
  // Scope inside the function (ie. of its block).
  private Scope ownScope;

  public FunctionType(
    Type[] parameterTypes,
    Type returnType,
    String name,
    CallTarget callTarget
  ) {
    this.parameterTypes = parameterTypes;
    this.returnType = returnType;
    this.name = name;
    this.callTarget = callTarget;
  }

  public Type[] getParameterTypes() {
    return this.parameterTypes;
  }

  public Type getReturnType() {
    return this.returnType;
  }

  public String getName() {
    return this.name;
  }

  public CallTarget getCallTarget() {
    return this.callTarget;
  }

  public Scope getOwnScope() {
    if (this.ownScope != null && !this.ownScope.isClosed()) {
      throw new RuntimeException("Scope not yet closed");
    }
    return this.ownScope;
  }

  public void setOwnScope(Scope scope) {
    if (this.ownScope != null) {
      throw new RuntimeException("Cannot re-set scope");
    }
    this.ownScope = scope;
  }

  public Scope getDeclarationScope() {
    if (this.declarationScope != null && !this.declarationScope.isClosed()) {
      throw new RuntimeException("Scope not yet closed");
    }
    return this.declarationScope;
  }

  public void setDeclarationScope(Scope declarationScope) {
    if (this.declarationScope != null) {
      throw new RuntimeException("Cannot re-set scope");
    }
    this.declarationScope = declarationScope;
  }

  @Override
  public Property getProperty(String name) throws PropertyNotFoundException {
    throw new PropertyNotFoundException("Not yet implemented");
  }
}
