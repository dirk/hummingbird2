package org.hummingbirdlang;

import com.oracle.truffle.api.frame.VirtualFrame;

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

  @Override
  public Object executeGeneric(VirtualFrame frame) {
    return null;
  }
}
