package org.hummingbirdlang.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;

public class HBLetDeclarationNode extends HBStatementNode {
  private final String left;
  @Child private HBExpressionNode rightNode;

  public HBLetDeclarationNode(String left, HBExpressionNode rightNode) {
    this.left = left;
    this.rightNode = rightNode;
  }

  @Override
  public void executeVoid(VirtualFrame frame) {
    return;
  }

  @Override
  public String toString() {
    return "let " + this.left + " = " + this.rightNode.toString();
  }
}
