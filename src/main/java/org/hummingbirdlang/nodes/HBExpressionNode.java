package org.hummingbirdlang;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo(language = "HB")
public abstract class HBExpressionNode extends HBStatementNode {
  // Execute with a generic unspecialized return value.
  public abstract Object executeGeneric(VirtualFrame frame);

  @Override
  public void executeVoid(VirtualFrame frame) {
    executeGeneric(frame);
  }
}
