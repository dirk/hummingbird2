package org.hummingbirdlang.nodes.arguments;

import org.hummingbirdlang.nodes.HBNode;

import com.oracle.truffle.api.frame.VirtualFrame;

public class GetTargetNode extends HBNode {
  @Override
  public Object executeGeneric(VirtualFrame frame) {
    Object[] arguments = frame.getArguments();
    return arguments[Layout.TARGET_INDEX];
  }
}
