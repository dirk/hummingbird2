package org.hummingbirdlang.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;

import org.hummingbirdlang.types.realize.InferenceVisitor;

public class HBStringLiteralNode extends HBExpressionNode {
  private final String value;

  public HBStringLiteralNode(String value) {
    this.value = value;
  }

  public void accept(InferenceVisitor visitor) {
    return;
  }

  @Override
  public Object executeGeneric(VirtualFrame frame) {
    return this.value;
  }
}
