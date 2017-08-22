package org.hummingbirdlang.nodes.arguments;

import com.oracle.truffle.api.frame.VirtualFrame;

import org.hummingbirdlang.nodes.HBNode;

public class GetThisNode extends HBNode {
  @Override
  public Object executeGeneric(VirtualFrame frame) {
    Object[] arguments = frame.getArguments();
    return arguments[Layout.RECEIVER_INDEX];
  }
}
