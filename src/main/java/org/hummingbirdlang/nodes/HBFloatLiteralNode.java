package org.hummingbirdlang;

import com.oracle.truffle.api.frame.VirtualFrame;

public class HBFloatLiteralNode extends HBExpressionNode {
  private final double value;

  HBFloatLiteralNode(String value) {
    this.value = Double.parseDouble(value);
  }

  @Override
  public Object executeGeneric(VirtualFrame frame) {
    return this.value;
  }
}
