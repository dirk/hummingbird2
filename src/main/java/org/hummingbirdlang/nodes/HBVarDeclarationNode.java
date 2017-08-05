package org.hummingbirdlang.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;

import org.hummingbirdlang.types.realize.InferenceVisitor;

public class HBVarDeclarationNode extends HBStatementNode {
  private final String left;
  @Child private HBExpressionNode rightNode;

  public HBVarDeclarationNode(String left, HBExpressionNode rightNode) {
    this.left = left;
    this.rightNode = rightNode;
  }

  public void accept(InferenceVisitor visitor) {
    return;
  }

  @Override
  public Object executeGeneric(VirtualFrame frame) {
    return null;
  }

  @Override
  public String toString() {
    return "var " + this.left + " = " + this.rightNode.toString();
  }
}
