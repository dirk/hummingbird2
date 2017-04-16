package org.hummingbirdlang.nodes;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import com.oracle.truffle.api.frame.VirtualFrame;

public class HBCallNode extends HBExpressionNode {
  @Child private HBExpressionNode targetNode;
  @Children private final HBExpressionNode[] parameterNodes;

  public HBCallNode(HBExpressionNode targetNode, HBExpressionNode[] parameterNodes) {
    this.targetNode = targetNode;
    this.parameterNodes = parameterNodes;
  }

  @Override
  public Object executeGeneric(VirtualFrame frame) {
    return null;
  }

  @Override
  public String toString() {
    StringBuilder result = new StringBuilder(this.targetNode.toString());
    result.append("(");
    List<String> parameters = Arrays.stream(this.parameterNodes).map(p -> p.toString()).collect(Collectors.toList());
    result.append(String.join(", ", parameters));
    result.append(")");
    return result.toString();
  }
}
