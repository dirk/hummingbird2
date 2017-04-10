package org.hummingbirdlang.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;

public class HBCallNode extends HBExpressionNode {
  @Child private HBExpressionNode targetNode;
  @Children private final HBExpressionNode[] parameterNodes;

  public HBCallNode(HBExpressionNode targetNode, HBExpressionNode[] parameterNodes) {
    this.targetNode = targetNode;
    this.parameterNodes = parameterNodes;
  }

  @Override
  public Object executeGeneric(VirtualFrame frame) {
    return null;
  }
}
