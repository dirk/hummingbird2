package org.hummingbirdlang.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo(shortName = "&&")
public class HBLogicalAndNode extends HBBinaryOperatorNode {
  public HBLogicalAndNode(HBExpressionNode leftNode, HBExpressionNode rightNode) {
    super(leftNode, rightNode);
  }

  @Override
  public Object executeGeneric(VirtualFrame frame) {
    // TODO: Better truthiness!
    Object leftValue = this.leftNode.executeGeneric(frame);
    Object rightValue = this.rightNode.executeGeneric(frame);
    return (leftValue != null) && (rightValue != null);
  }
}
