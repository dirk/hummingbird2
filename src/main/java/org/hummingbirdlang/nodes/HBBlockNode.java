package org.hummingbirdlang;

import com.oracle.truffle.api.frame.VirtualFrame;

public class HBBlockNode extends HBStatementNode {
  @Children private final HBStatementNode[] bodyNodes;

  public HBBlockNode(HBStatementNode[] bodyNodes) {
    this.bodyNodes = bodyNodes;
  }

  @Override
  public void executeVoid(VirtualFrame frame) {
    for (HBStatementNode bodyNode : bodyNodes) {
      bodyNode.executeVoid(frame);
    }
  }
}
