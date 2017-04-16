package org.hummingbirdlang.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;

import org.hummingbirdlang.types.realize.InferenceVisitor;

public class HBFloatLiteralNode extends HBExpressionNode {
  private final double value;

  public HBFloatLiteralNode(String value) {
    this.value = Double.parseDouble(value);
  }

  public void accept(InferenceVisitor visitor) {
    return;
  }

  @Override
  public Object executeGeneric(VirtualFrame frame) {
    return this.value;
  }
}
