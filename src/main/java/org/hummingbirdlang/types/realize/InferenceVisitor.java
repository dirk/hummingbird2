package org.hummingbirdlang.types.realize;

import org.hummingbirdlang.nodes.HBAssignmentNode;
import org.hummingbirdlang.nodes.HBBlockNode;
import org.hummingbirdlang.nodes.HBCallNode;
import org.hummingbirdlang.nodes.HBExpressionNode;
import org.hummingbirdlang.nodes.HBFunctionNode;
import org.hummingbirdlang.nodes.HBIdentifierNode;
import org.hummingbirdlang.nodes.HBIntegerLiteralNode;
import org.hummingbirdlang.nodes.HBLetDeclarationNode;
import org.hummingbirdlang.nodes.HBLogicalAndNode;
import org.hummingbirdlang.nodes.HBPropertyNode;
import org.hummingbirdlang.nodes.HBReturnNode;
import org.hummingbirdlang.nodes.HBSourceRootNode;
import org.hummingbirdlang.nodes.HBStringLiteralNode;
import org.hummingbirdlang.types.composite.SumType;
import org.hummingbirdlang.types.concrete.BooleanType;
import org.hummingbirdlang.types.concrete.IntegerType;
import org.hummingbirdlang.types.concrete.MethodType;
import org.hummingbirdlang.types.concrete.NullType;
import org.hummingbirdlang.types.concrete.StringType;
import org.hummingbirdlang.types.FunctionType;
import org.hummingbirdlang.types.Property;
import org.hummingbirdlang.types.PropertyNotFoundException;
import org.hummingbirdlang.types.Type;
import org.hummingbirdlang.types.TypeMismatchException;
import org.hummingbirdlang.types.UnknownType;
import org.hummingbirdlang.types.scope.BuiltinScope;
import org.hummingbirdlang.types.scope.LocalScope;
import org.hummingbirdlang.types.scope.NameNotFoundException;
import org.hummingbirdlang.types.scope.Resolution;
import org.hummingbirdlang.types.scope.Scope;
import org.hummingbirdlang.types.scope.SourceScope;

/**
 * Visitor-pattern system for recursively inferring type information on
 * nodes in the AST.
 */
public final class InferenceVisitor {
  private final Index index;
  private BuiltinScope builtinScope;
  private Scope currentScope;

  public InferenceVisitor(Index index) {
    this.builtinScope = new BuiltinScope(index);
    this.currentScope = new SourceScope(this.builtinScope);
    this.index = index;
  }

  private void pushScope() {
    Scope parentScope = this.currentScope;
    this.currentScope = new LocalScope(parentScope);
  }

  private void popScope() {
    this.currentScope = this.currentScope.getParent();
  }

  public void enter(HBSourceRootNode rootNode) {
    rootNode.setBuiltinScope(this.builtinScope);
  }

  public void leave(HBSourceRootNode rootNode) {
    // Current scope should be the `SourceScope`.
    if (!(this.currentScope instanceof SourceScope)) {
      throw new RuntimeException("Did not return to SourceScope");
    }
    this.currentScope.close();
  }

  public void enter(HBFunctionNode functionNode) {
    // TODO: Actually set and/or infer parameter and return types.
    FunctionType functionType = new FunctionType(
      new Type[]{},
      new UnknownType(),
      functionNode.getName(),
      functionNode.getCallTarget()
    );
    functionNode.setFunctionType(functionType);
    functionType.setDeclarationScope(this.currentScope);
    this.currentScope.setLocal(functionNode.getName(), functionType);
    this.pushScope();
    functionType.setOwnScope(this.currentScope);
  }

  public void leave(HBFunctionNode functionNode) {
    this.currentScope.close();
    this.popScope();
  }

  public void enter(HBBlockNode blockNode) {
    return;
  }

  public void leave(HBBlockNode blockNode) {
    return;
  }

  public void visit(HBAssignmentNode assignmentNode) throws TypeMismatchException {
    Type targetType = assignmentNode.getTargetNode().getType();
    Type valueType = assignmentNode.getValueNode().getType();
    targetType.assertEquals(valueType);
  }

  public void visit(HBCallNode callNode) {
    Type targetType = callNode.getTargetNode().getType();
    Type returnType;
    if (targetType instanceof FunctionType) {
      FunctionType functionType = (FunctionType)targetType;
      returnType = functionType.getReturnType();
    } else if (targetType instanceof MethodType) {
      MethodType methodType = (MethodType)targetType;
      returnType = methodType.getReturnType();
    } else {
      throw new RuntimeException("Cannot call target of type: " + String.valueOf(targetType));
    }
    callNode.setType(returnType);
  }

  public void visit(HBIdentifierNode identifierNode) throws NameNotFoundException {
    Resolution resolution = this.currentScope.resolve(identifierNode.getName());
    identifierNode.setResolution(resolution);
  }

  public void visit(HBIntegerLiteralNode literalNode) {
    literalNode.setType(this.index.getBuiltin().getType(IntegerType.BUILTIN_NAME));
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
      resultType = new SumType(new Type[]{ BooleanType.SINGLETON, rightType, });
    } else {
      resultType = BooleanType.SINGLETON;
    }
    andNode.setType(resultType);
  }

  public void visit(HBPropertyNode propertyNode) throws PropertyNotFoundException {
    Type targetType = propertyNode.getTargetNode().getType();
    Property property = targetType.getProperty(propertyNode.getPropertyName());
    propertyNode.setProperty(property);
    propertyNode.setType(property.getType());
  }

  public void visit(HBReturnNode returnNode) {
    Type returnType = NullType.SINGLETON;
    HBExpressionNode expressionNode = returnNode.getExpressionNode();
    if (expressionNode != null) {
      returnType = expressionNode.getType();
    }
    returnNode.setReturnType(returnType);
  }

  public void visit(HBStringLiteralNode literalNode) {
    literalNode.setType(this.index.getBuiltin().getType(StringType.BUILTIN_NAME));
  }
}
