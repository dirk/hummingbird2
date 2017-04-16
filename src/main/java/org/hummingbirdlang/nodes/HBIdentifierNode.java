package org.hummingbirdlang.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;

import org.hummingbirdlang.parser.Token;

// Basic identifier of a local or global name.
public class HBIdentifierNode extends HBExpressionNode {
  private final String name;

  public HBIdentifierNode(Token token) {
    this.name = token.val;
  }

  @Override
  public Object executeGeneric(VirtualFrame frame) {
    return null;
  }

  @Override
  public String toString() {
    return this.name;
  }
}
