package org.hummingbirdlang.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;

import org.hummingbirdlang.parser.Token;

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
