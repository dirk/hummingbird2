package org.hummingbirdlang;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo(shortName = "||")
public class HBLogicalOrNode extends HBBinaryOperatorNode {
  public HBLogicalOrNode(HBExpressionNode leftNode, HBExpressionNode rightNode) {
    super(leftNode, rightNode);
  }

  @Override
  public Object executeGeneric(VirtualFrame frame) {
    // TODO: Better truthiness!
    Object leftValue = this.leftNode.executeGeneric(frame);
    if (leftValue != null) {
      return true;
    } else {
      Object rightValue = this.rightNode.executeGeneric(frame);
      return (rightValue != null);
    }
  }
}
