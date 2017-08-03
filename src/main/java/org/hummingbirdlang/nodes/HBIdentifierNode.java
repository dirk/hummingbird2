package org.hummingbirdlang.nodes;

import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.VirtualFrame;

import org.hummingbirdlang.parser.Token;
import org.hummingbirdlang.types.realize.InferenceVisitor;
import org.hummingbirdlang.types.scope.NameNotFoundException;

// Basic identifier of a local or global name.
public class HBIdentifierNode extends HBExpressionNode {
  private final String name;

  public HBIdentifierNode(Token token) {
    this.name = token.val;
  }

  public void accept(InferenceVisitor visitor) throws NameNotFoundException {
    visitor.visit(this);
  }

  public String getName() {
    return this.name;
  }

  @Override
  public Object executeGeneric(VirtualFrame frame) {
    FrameSlot frameSlot = frame.getFrameDescriptor().findOrAddFrameSlot(this.name);
    return frame.getValue(frameSlot);
  }

  @Override
  public String toString() {
    return this.name;
  }
}
