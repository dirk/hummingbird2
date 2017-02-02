package org.hummingbirdlang;

import com.oracle.truffle.api.frame.VirtualFrame;

public class HBAssignmentNode extends HBExpressionNode {
  @Child private HBExpressionNode targetNode;
  @Child private HBExpressionNode valueNode;

  public HBAssignmentNode(HBExpressionNode targetNode, HBExpressionNode valueNode) {
    this.targetNode = targetNode;
    this.valueNode = valueNode;
  }

  @Override
  public Object executeGeneric(VirtualFrame frame) {
    return null;
  }
}
