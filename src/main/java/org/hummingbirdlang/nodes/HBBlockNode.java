package org.hummingbirdlang;

public class HBBlockNode extends HBStatementNode {
  @Children private final HBStatementNode[] bodyNodes;

  public HBBlockNode(HBStatementNode[] bodyNodes) {
    this.bodyNodes = bodyNodes;
  }
}
