package org.hummingbirdlang.types.realize;

import org.hummingbirdlang.nodes.HBBlockNode;
import org.hummingbirdlang.nodes.HBFunctionNode;
import org.hummingbirdlang.nodes.HBIntegerLiteralNode;
import org.hummingbirdlang.nodes.HBLogicalAndNode;
import org.hummingbirdlang.nodes.HBSourceRootNode;
import org.hummingbirdlang.types.composite.SumType;
import org.hummingbirdlang.types.concrete.BooleanType;
import org.hummingbirdlang.types.concrete.IntegerType;
import org.hummingbirdlang.types.concrete.NullType;
import org.hummingbirdlang.types.Type;

/**
 * Visitor-pattern system for recursively inferring type information on
 * nodes in the AST.
 */
public final class InferenceVisitor {
  public void enter(HBSourceRootNode rootNode) {
    return;
  }

  public void leave(HBSourceRootNode rootNode) {
    return;
  }

  public void enter(HBFunctionNode functionNode) {
    return;
  }

  public void leave(HBFunctionNode functionNode) {
    return;
  }

  public void enter(HBBlockNode blockNode) {
    return;
  }

  public void leave(HBBlockNode blockNode) {
    return;
  }

  public void visit(HBIntegerLiteralNode literalNode) {
    literalNode.setType(IntegerType.SINGLETON);
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
}
