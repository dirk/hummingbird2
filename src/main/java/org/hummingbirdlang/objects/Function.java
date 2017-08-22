package org.hummingbirdlang.objects;

import com.oracle.truffle.api.CallTarget;

import org.hummingbirdlang.types.FunctionType;

public final class Function {
  private final FunctionType type;
  private final Bindings bindings;
  private final CallTarget callTarget;
  private final String name;

  public Function(FunctionType type, Bindings bindings) {
    this.type = type;
    this.bindings = bindings;
    this.callTarget = type.getCallTarget();
    this.name = type.getName();
  }

  public FunctionType getType() {
    return this.type;
  }

  public Bindings getBindings() {
    return this.bindings;
  }

  public CallTarget getCallTarget() {
    return this.callTarget;
  }
}
