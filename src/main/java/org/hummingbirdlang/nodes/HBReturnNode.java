package org.hummingbirdlang.nodes;

import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node.Child;

import org.hummingbirdlang.nodes.HBExpressionNode;
import org.hummingbirdlang.nodes.HBStatementNode;
import org.hummingbirdlang.types.Type;
import org.hummingbirdlang.types.TypeException;
import org.hummingbirdlang.types.realize.InferenceVisitor;

public abstract class HBReturnNode extends HBStatementNode {
  @Child protected HBExpressionNode expressionNode;
  @CompilationFinal Type returnType;

  public HBReturnNode() {
    this.expressionNode = null;
  }

  public HBReturnNode(HBExpressionNode expressionNode) {
    this.expressionNode = expressionNode;
  }

  public void accept(InferenceVisitor visitor) throws TypeException {
    if (this.expressionNode != null) {
      this.expressionNode.accept(visitor);
    }
    visitor.visit(this);
  }

  public HBExpressionNode getExpressionNode() {
    return this.expressionNode;
  }

  public Type getReturnType() {
    return this.returnType;
  }

  public void setReturnType(Type returnType) {
    this.returnType = returnType;
  }

  @Specialization(guards = {"expressionNode != null"})
  public Object returnUsingExpression(VirtualFrame frame) {
    Object returnValue = this.expressionNode.executeGeneric(frame);
    // TODO: Raise a return control flow exception.
    return null;
  }

  @Specialization(guards = {"expressionNode == null"})
  public Object returnNull(VirtualFrame frame) {
    // TODO: Raise control flow exception.
    return null;
  }
}
