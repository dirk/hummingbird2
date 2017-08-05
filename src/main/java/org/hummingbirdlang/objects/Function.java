package org.hummingbirdlang.objects;

import com.oracle.truffle.api.CallTarget;

import org.hummingbirdlang.types.FunctionType;

public final class Function {
  private final CallTarget callTarget;
  private final String name;

  public Function(FunctionType type) {
    this.callTarget = type.getCallTarget();
    this.name = type.getName();
  }

  public CallTarget getCallTarget() {
    return this.callTarget;
  }
}
