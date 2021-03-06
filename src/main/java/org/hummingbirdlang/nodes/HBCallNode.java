package org.hummingbirdlang.nodes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.frame.VirtualFrame;

import org.hummingbirdlang.nodes.arguments.Layout;
import org.hummingbirdlang.nodes.frames.GetBindingsNodeGen;
import org.hummingbirdlang.nodes.frames.GetLocalNodeGen;
import org.hummingbirdlang.runtime.bindings.Bindings;
import org.hummingbirdlang.objects.Function;
import org.hummingbirdlang.objects.Method;
import org.hummingbirdlang.types.TypeException;
import org.hummingbirdlang.types.realize.InferenceVisitor;
import org.hummingbirdlang.types.scope.Resolution;
import org.hummingbirdlang.types.scope.Scope;

public class HBCallNode extends HBExpressionNode {
  @Child private HBExpressionNode targetNode;
  @Children private final HBExpressionNode[] argumentNodes;

  public HBCallNode(HBExpressionNode targetNode, HBExpressionNode[] argumentNodes) {
    this.targetNode = targetNode;
    this.argumentNodes = argumentNodes;
  }

  public void accept(InferenceVisitor visitor) throws TypeException {
    this.targetNode.accept(visitor);
    for (HBExpressionNode parameterNode : argumentNodes) {
      parameterNode.accept(visitor);
    }
    visitor.visit(this);
  }

  public HBExpressionNode getTargetNode() {
    return this.targetNode;
  }

  public HBExpressionNode[] getArgumentNodes() {
    return this.argumentNodes;
  }

  @Override
  public Object executeGeneric(VirtualFrame frame) {
    Object target = this.targetNode.executeGeneric(frame);
    CallTarget callTarget;

    Layout layout = new Layout(target, null);

    for (HBNode argumentNode : this.argumentNodes) {
      Object argumentValue = argumentNode.executeGeneric(frame);
      layout.addArgument(argumentValue);
    }

    if (target instanceof Method) {
      Method method = (Method)target;
      callTarget = method.getCallTarget();
      layout.setReceiver(method.getReceiver());

    } else if (target instanceof Function) {
      Function function = (Function)target;
      callTarget = function.getCallTarget();

    } else {
      throw new Error("Cannot call to: " + target.toString());
    }

    Object[] arguments = layout.getCallArguments();
    return callTarget.call(arguments);
  }

  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();
    result.append("HBCallNode(");
    result.append(this.targetNode.toString());
    result.append(", (");
    List<String> parameters = Arrays.stream(this.argumentNodes).map(p -> p.toString()).collect(Collectors.toList());
    result.append(String.join(", ", parameters));
    result.append(")");
    result.append(")");
    return result.toString();
  }
}
