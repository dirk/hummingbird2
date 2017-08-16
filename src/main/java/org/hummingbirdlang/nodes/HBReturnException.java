package org.hummingbirdlang.nodes;

import com.oracle.truffle.api.nodes.ControlFlowException;

public final class HBReturnException extends ControlFlowException {
  private final Object returnValue;

  public HBReturnException(Object returnValue) {
    this.returnValue = returnValue;
  }

  public Object getReturnValue() {
    return this.returnValue;
  }
}
