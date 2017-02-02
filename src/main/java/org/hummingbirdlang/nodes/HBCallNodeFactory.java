package org.hummingbirdlang;

import java.util.List;

public class HBCallNodeFactory {
  public static HBCallNode create(HBExpressionNode parent, List<HBExpressionNode> parameters) {
    HBExpressionNode[] parametersArray = parameters.toArray(new HBExpressionNode[parameters.size()]);
    return new HBCallNode(parent, parametersArray);
  }
}
