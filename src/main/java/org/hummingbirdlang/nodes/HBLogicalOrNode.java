package org.hummingbirdlang.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import org.hummingbirdlang.types.realize.InferenceVisitor;

@NodeInfo(shortName = "||")
public class HBLogicalOrNode extends HBBinaryOperatorNode {
  public HBLogicalOrNode(HBExpressionNode leftNode, HBExpressionNode rightNode) {
    super(leftNode, rightNode);
  }

  public void accept(InferenceVisitor visitor) {
    return;
  }

  @Override
  public Object executeGeneric(VirtualFrame frame) throws Exception {
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
