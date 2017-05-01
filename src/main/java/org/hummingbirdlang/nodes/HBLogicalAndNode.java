package org.hummingbirdlang.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import org.hummingbirdlang.types.realize.InferenceVisitor;

@NodeInfo(shortName = "&&")
public class HBLogicalAndNode extends HBBinaryOperatorNode {
  public HBLogicalAndNode(HBExpressionNode leftNode, HBExpressionNode rightNode) {
    super(leftNode, rightNode);
  }

  public void accept(InferenceVisitor visitor) {
    this.leftNode.accept(visitor);
    this.rightNode.accept(visitor);
    visitor.visit(this);
  }

  @Override
  public Object executeGeneric(VirtualFrame frame) {
    // TODO: Better truthiness!
    Object leftValue = this.leftNode.executeGeneric(frame);
    Object rightValue = this.rightNode.executeGeneric(frame);
    return (leftValue != null) && (rightValue != null);
  }
}
