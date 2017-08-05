package org.hummingbirdlang.nodes;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.frame.VirtualFrame;

import org.hummingbirdlang.builtins.Method;
import org.hummingbirdlang.types.TypeException;
import org.hummingbirdlang.types.realize.InferenceVisitor;

public class HBCallNode extends HBExpressionNode {
  @Child private HBExpressionNode targetNode;
  @Children private final HBExpressionNode[] parameterNodes;

  public HBCallNode(HBExpressionNode targetNode, HBExpressionNode[] parameterNodes) {
    this.targetNode = targetNode;
    this.parameterNodes = parameterNodes;
  }

  public void accept(InferenceVisitor visitor) throws TypeException {
    this.targetNode.accept(visitor);
    for (HBExpressionNode parameterNode : parameterNodes) {
      parameterNode.accept(visitor);
    }
    visitor.visit(this);
  }

  public HBExpressionNode getTargetNode() {
    return this.targetNode;
  }

  public HBExpressionNode[] getParameterNodes() {
    return this.parameterNodes;
  }

  @Override
  public Object executeGeneric(VirtualFrame frame) {
    Object target = this.targetNode.executeGeneric(frame);

    if (target instanceof Method) {
      Method method = (Method)target;
      CallTarget callTarget = method.getCallTarget();
      return callTarget.call(method.getReceiver());
    } else {
      throw new Error("Cannot call to: " + target.toString());
    }
  }

  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();
    result.append("HBCallNode(");
    result.append(this.targetNode.toString());
    result.append(", (");
    List<String> parameters = Arrays.stream(this.parameterNodes).map(p -> p.toString()).collect(Collectors.toList());
    result.append(String.join(", ", parameters));
    result.append(")");
    result.append(")");
    return result.toString();
  }
}
