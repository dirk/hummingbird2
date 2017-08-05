package org.hummingbirdlang.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo(language = "HB")
public abstract class HBNode extends Node {
  // Execute with a generic unspecialized return value.
  public abstract Object executeGeneric(VirtualFrame frame);

  // Execute without a return value.
  public void executeVoid(VirtualFrame frame) {
    executeGeneric(frame);
  }
}
