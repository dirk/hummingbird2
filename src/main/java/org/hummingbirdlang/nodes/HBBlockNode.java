package org.hummingbirdlang.nodes;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import com.oracle.truffle.api.frame.VirtualFrame;

import org.hummingbirdlang.types.realize.InferenceVisitor;
import org.hummingbirdlang.types.scope.NameNotFoundException;

public class HBBlockNode extends HBStatementNode {
  @Children private final HBStatementNode[] bodyNodes;

  public HBBlockNode(HBStatementNode[] bodyNodes) {
    this.bodyNodes = bodyNodes;
  }

  public void accept(InferenceVisitor visitor) throws NameNotFoundException {
    visitor.enter(this);
    for (HBStatementNode node : this.bodyNodes) {
      node.accept(visitor);
    }
    visitor.leave(this);
  }

  @Override
  public void executeVoid(VirtualFrame frame) throws Exception {
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
