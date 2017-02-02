package org.hummingbirdlang;

// Basic identifier of a local or global name.
public abstract class HBBinaryOperatorNode extends HBExpressionNode {
  @Child protected HBExpressionNode leftNode;
  @Child protected HBExpressionNode rightNode;

  protected HBBinaryOperatorNode(HBExpressionNode leftNode, HBExpressionNode rightNode) {
    this.leftNode = leftNode;
    this.rightNode = rightNode;
  }
}