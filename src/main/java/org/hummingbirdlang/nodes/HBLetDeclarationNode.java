package org.hummingbirdlang.nodes;

import com.oracle.truffle.api.frame.FrameSlot;
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
  public Object executeGeneric(VirtualFrame frame) {
    Object value = this.rightNode.executeGeneric(frame);
    FrameSlot frameSlot = frame.getFrameDescriptor().findOrAddFrameSlot(this.left);
    frame.setObject(frameSlot, value);
    return null;
  }

  @Override
  public String toString() {
    StringBuilder result = new StringBuilder("HBLetDeclarationNode");
    result.append("(");
    result.append(this.left);
    result.append(" = ");
    result.append(this.rightNode.toString());
    result.append(")");
    return result.toString();
  }
}
