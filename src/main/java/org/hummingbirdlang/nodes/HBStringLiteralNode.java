package org.hummingbirdlang.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;

import org.hummingbirdlang.types.realize.InferenceVisitor;

public class HBStringLiteralNode extends HBExpressionNode {
  private final String value;

  public HBStringLiteralNode(String value) {
    assert value.charAt(0) == '"';
    assert value.charAt(value.length() - 1) == '"';
    this.value = value.substring(1, value.length() - 1);
  }

  public void accept(InferenceVisitor visitor) {
    return;
  }

  @Override
  public Object executeGeneric(VirtualFrame frame) {
    return this.value;
  }

  @Override
  public String toString() {
    return "\"" + this.value + "\"";
  }
}
