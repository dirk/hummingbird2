package org.hummingbirdlang;

import com.oracle.truffle.api.frame.VirtualFrame;

public class HBCallNode extends HBExpressionNode {
  @Child private HBExpressionNode targetNode;
  @Children private HBExpressionNode[] parameterNodes;

  public HBCallNode(HBExpressionNode targetNode, HBExpressionNode[] parameterNodes) {
    this.targetNode = targetNode;
    this.parameterNodes = parameterNodes;
  }

  @Override
  public Object executeGeneric(VirtualFrame frame) {
    return null;
  }
}
