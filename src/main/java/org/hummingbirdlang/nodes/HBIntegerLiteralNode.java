package org.hummingbirdlang;

import com.oracle.truffle.api.frame.VirtualFrame;

public class HBIntegerLiteralNode extends HBExpressionNode {
  private final long value;

  HBIntegerLiteralNode(String value) {
    this.value = Long.decode(value);
  }

  @Override
  public Object executeGeneric(VirtualFrame frame) {
    return this.value;
  }
}
