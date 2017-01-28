package org.hummingbirdlang;

import com.oracle.truffle.api.frame.VirtualFrame;

// Basic identifier of a local or global name.
public class HBIdentifierNode extends HBExpressionNode {
  private final String name;

  public HBIdentifierNode(Token token) {
    name = token.val;
  }

  @Override
  public Object executeGeneric(VirtualFrame frame) {
    return null;
  }
}
