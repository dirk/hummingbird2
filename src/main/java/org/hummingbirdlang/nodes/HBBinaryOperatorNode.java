package org.hummingbirdlang.nodes;

// Basic identifier of a local or global name.
public abstract class HBBinaryOperatorNode extends HBExpressionNode {
  @Child protected HBExpressionNode leftNode;
  @Child protected HBExpressionNode rightNode;

  protected HBBinaryOperatorNode(HBExpressionNode leftNode, HBExpressionNode rightNode) {
    this.leftNode = leftNode;
    this.rightNode = rightNode;
  }

  public HBExpressionNode getLeftNode() {
    return this.leftNode;
  }

  public HBExpressionNode getRightNode() {
    return this.rightNode;
  }
}
