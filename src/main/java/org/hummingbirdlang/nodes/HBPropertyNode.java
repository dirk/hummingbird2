package org.hummingbirdlang.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;

import org.hummingbirdlang.parser.Token;
import org.hummingbirdlang.types.realize.InferenceVisitor;

public class HBPropertyNode extends HBExpressionNode {
  @Child private HBExpressionNode targetNode;
  private final String property;

  public HBPropertyNode(HBExpressionNode targetNode, Token token) {
    this.targetNode = targetNode;
    this.property = token.val;
  }

  public HBPropertyNode(HBExpressionNode targetNode, String property) {
    this.targetNode = targetNode;
    this.property = property;
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
    return this.targetNode.toString() + "." + this.property.toString();
  }
}
