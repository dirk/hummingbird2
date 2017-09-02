package org.hummingbirdlang.nodes;

import com.oracle.truffle.api.frame.FrameSlot;
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
    if (!(this.targetNode instanceof HBIdentifierNode)) {
      throw new RuntimeException("Cannot yet handle assignment of non-identifier node: " + this.targetNode.toString());
    }
    HBIdentifierNode identifierNode = (HBIdentifierNode)this.targetNode;

    Object value = this.valueNode.executeGeneric(frame);
    FrameSlot frameSlot = frame.getFrameDescriptor().findFrameSlot(identifierNode.getName());
    frame.setObject(frameSlot, value);
    return value;
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
