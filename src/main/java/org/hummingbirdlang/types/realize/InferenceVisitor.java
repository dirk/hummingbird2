package org.hummingbirdlang.types.realize;

import org.hummingbirdlang.nodes.HBBlockNode;
import org.hummingbirdlang.nodes.HBCallNode;
import org.hummingbirdlang.nodes.HBFunctionNode;
import org.hummingbirdlang.nodes.HBIdentifierNode;
import org.hummingbirdlang.nodes.HBIntegerLiteralNode;
import org.hummingbirdlang.nodes.HBLetDeclarationNode;
import org.hummingbirdlang.nodes.HBLogicalAndNode;
import org.hummingbirdlang.nodes.HBPropertyNode;
import org.hummingbirdlang.nodes.HBSourceRootNode;
import org.hummingbirdlang.nodes.HBStringLiteralNode;
import org.hummingbirdlang.types.composite.SumType;
import org.hummingbirdlang.types.concrete.BooleanType;
import org.hummingbirdlang.types.concrete.IntegerType;
import org.hummingbirdlang.types.concrete.StringType;
import org.hummingbirdlang.types.Property;
import org.hummingbirdlang.types.PropertyNotFoundException;
import org.hummingbirdlang.types.Type;
import org.hummingbirdlang.types.scope.LocalScope;
import org.hummingbirdlang.types.scope.NameNotFoundException;
import org.hummingbirdlang.types.scope.Scope;
import org.hummingbirdlang.types.scope.SourceScope;

/**
 * Visitor-pattern system for recursively inferring type information on
 * nodes in the AST.
 */
public final class InferenceVisitor {
  private Scope currentScope;

  public InferenceVisitor() {
    this.currentScope = new SourceScope();
  }

  private void pushScope() {
    Scope parentScope = this.currentScope;
    this.currentScope = new LocalScope(parentScope);
  }

  private void popScope() {
    this.currentScope = this.currentScope.getParent();
  }

  public void enter(HBSourceRootNode rootNode) {
    return;
  }

  public void leave(HBSourceRootNode rootNode) {
    return;
  }

  public void enter(HBFunctionNode functionNode) {
    this.pushScope();
  }

  public void leave(HBFunctionNode functionNode) {
    this.popScope();
  }

  public void enter(HBBlockNode blockNode) {
    return;
  }

  public void leave(HBBlockNode blockNode) {
    return;
  }

  public void visit(HBCallNode callNode) {
    Type targetType = callNode.getTargetNode().getType();
  }

  public void visit(HBIdentifierNode identifierNode) throws NameNotFoundException {
    Type type = this.currentScope.get(identifierNode.getName());
    identifierNode.setType(type);
  }

  public void visit(HBIntegerLiteralNode literalNode) {
    literalNode.setType(IntegerType.SINGLETON);
  }

  public void visit(HBLetDeclarationNode letNode) {
    Type rightType = letNode.getRightNode().getType();
    String left = letNode.getLeft();
    this.currentScope.setLocal(left, rightType);
  }

  public void visit(HBLogicalAndNode andNode) {
    Type leftType = andNode.getLeftNode().getType();
    Type rightType = andNode.getRightNode().getType();

    Type resultType;
    if (!rightType.equals(BooleanType.SINGLETON)) {
      resultType = new SumType(BooleanType.SINGLETON, rightType);
    } else {
      resultType = BooleanType.SINGLETON;
    }
    andNode.setType(resultType);
  }

  public void visit(HBPropertyNode propertyNode) throws PropertyNotFoundException {
    Type targetType = propertyNode.getTargetNode().getType();
    Property type = targetType.getProperty(propertyNode.getProperty());
  }

  public void visit(HBStringLiteralNode literalNode) {
    literalNode.setType(StringType.SINGLETON);
  }
}
