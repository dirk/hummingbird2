package org.hummingbirdlang.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;

import org.hummingbirdlang.types.realize.InferenceVisitor;

public class HBAssignmentNode extends HBExpressionNode {
  @Child private HBExpressionNode targetNode;
  @Child private HBExpressionNode valueNode;

  public HBAssignmentNode(HBExpressionNode targetNode, HBExpressionNode valueNode) {
    this.targetNode = targetNode;
    this.valueNode = valueNode;
  }

  public void accept(InferenceVisitor visitor) {
    return;
  }

  @Override
  public Object executeGeneric(VirtualFrame frame) {
    return null;
  }

  @Override
  public String toString() {
    StringBuilder result = new StringBuilder("HBAssignmentNode");
    result.append("(");
    result.append(this.targetNode.toString());
    result.append(" = ");
    result.append(this.valueNode.toString());
    result.append(")");
    return result.toString();
  }
}
