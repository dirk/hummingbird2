package org.hummingbirdlang.nodes;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
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

  @Override
  public String toString() {
    List<String> nodes = Arrays.stream(this.bodyNodes).map(n -> String.valueOf(n)).collect(Collectors.toList());
    return "{" + String.join(", ", nodes) + "}";
  }
}
