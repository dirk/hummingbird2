package org.hummingbirdlang.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;

public class HBVarDeclarationNode extends HBStatementNode {
  private final String left;
  @Child private HBExpressionNode rightNode;

  public HBVarDeclarationNode(String left, HBExpressionNode rightNode) {
    this.left = left;
    this.rightNode = rightNode;
  }

  @Override
  public void executeVoid(VirtualFrame frame) {
    return;
  }

  @Override
  public String toString() {
    return "var " + this.left + " = " + this.rightNode.toString();
  }
}
