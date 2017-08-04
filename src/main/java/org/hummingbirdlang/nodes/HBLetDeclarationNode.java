package org.hummingbirdlang.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;

import org.hummingbirdlang.types.TypeException;
import org.hummingbirdlang.types.realize.InferenceVisitor;

public class HBLetDeclarationNode extends HBStatementNode {
  private final String left;
  @Child private HBExpressionNode rightNode;

  public HBLetDeclarationNode(String left, HBExpressionNode rightNode) {
    this.left = left;
    this.rightNode = rightNode;
  }

  public void accept(InferenceVisitor visitor) throws TypeException {
    this.rightNode.accept(visitor);
    visitor.visit(this);
  }

  public String getLeft() {
    return this.left;
  }

  public HBExpressionNode getRightNode() {
    return this.rightNode;
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
