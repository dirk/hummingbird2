package org.hummingbirdlang.nodes.arguments;

import com.oracle.truffle.api.frame.VirtualFrame;

import org.hummingbirdlang.nodes.HBNode;

public class GetArgumentNode extends HBNode {
  private final int index;

  public GetArgumentNode(int index) {
    this.index = index;
  }

  @Override
  public Object executeGeneric(VirtualFrame frame) {
    Object[] arguments = frame.getArguments();
    return arguments[this.index];
  }
}
