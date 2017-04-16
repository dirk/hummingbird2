package org.hummingbirdlang.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;

import org.hummingbirdlang.types.realize.InferenceVisitor;

public class HBIndexerNode extends HBExpressionNode {
  @Child private HBExpressionNode targetNode;
  @Child private HBExpressionNode indexNode;

  public HBIndexerNode(HBExpressionNode targetNode, HBExpressionNode indexNode) {
    this.targetNode = targetNode;
    this.indexNode = indexNode;
  }

  public void accept(InferenceVisitor visitor) {
    return;
  }

  @Override
  public Object executeGeneric(VirtualFrame frame) {
    return null;
  }
}
