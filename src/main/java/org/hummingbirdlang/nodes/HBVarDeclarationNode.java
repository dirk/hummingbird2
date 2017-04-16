package org.hummingbirdlang.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;

public class HBVarDeclarationNode extends HBStatementNode {
  private final String left;
  @Child private HBExpressionNode right;

  public HBVarDeclarationNode(String left, HBExpressionNode right) {
    this.left = left;
    this.right = right;
  }

  @Override
  public void executeVoid(VirtualFrame frame) {
    return;
  }

  @Override
  public String toString() {
    return "var " + this.left + " = " + this.right.toString();
  }
}
