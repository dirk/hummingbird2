package org.hummingbirdlang;

import com.oracle.truffle.api.frame.VirtualFrame;

public class HBIndexerNode extends HBExpressionNode {
  @Child private HBExpressionNode targetNode;
  @Child private HBExpressionNode indexNode;

  public HBIndexerNode(HBExpressionNode targetNode, HBExpressionNode indexNode) {
    this.targetNode = targetNode;
    this.indexNode = indexNode;
  }

  @Override
  public Object executeGeneric(VirtualFrame frame) {
    return null;
  }
}
