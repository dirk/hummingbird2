package org.hummingbirdlang.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;

public class HBLetDeclarationNode extends HBStatementNode {
  private final String left;
  @Child private HBExpressionNode right;

  public HBLetDeclarationNode(String left, HBExpressionNode right) {
    this.left = left;
    this.right = right;
  }

  @Override
  public void executeVoid(VirtualFrame frame) {
    return;
  }

  @Override
  public String toString() {
    return "let " + this.left + " = " + this.right.toString();
  }
}
