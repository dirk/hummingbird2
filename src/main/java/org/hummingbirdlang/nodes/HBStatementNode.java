package org.hummingbirdlang;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo(language = "HB")
public abstract class HBStatementNode extends Node {
  // Execute without a return value.
  public abstract void executeVoid(VirtualFrame frame);
}
