package org.hummingbirdlang.nodes;

import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.VirtualFrame;

import org.hummingbirdlang.types.TypeException;
import org.hummingbirdlang.types.realize.InferenceVisitor;

public class HBAssignmentNode extends HBExpressionNode {
  @Child private HBExpressionNode targetNode;
  @Child private HBExpressionNode valueNode;

  public HBAssignmentNode(HBExpressionNode targetNode, HBExpressionNode valueNode) {
    this.targetNode = targetNode;
    this.valueNode = valueNode;
  }

  public void accept(InferenceVisitor visitor) throws TypeException {
    this.targetNode.accept(visitor);
    this.valueNode.accept(visitor);
    visitor.visit(this);
  }

  public HBExpressionNode getTargetNode() {
    return this.targetNode;
  }

  public HBExpressionNode getValueNode() {
    return this.valueNode;
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
