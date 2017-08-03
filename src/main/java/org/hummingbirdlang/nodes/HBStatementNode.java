package org.hummingbirdlang.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;

import org.hummingbirdlang.types.realize.Visitable;

@NodeInfo(language = "HB")
public abstract class HBStatementNode extends Node implements Visitable {
  // Execute without a return value.
  public abstract void executeVoid(VirtualFrame frame) throws Exception;
}
