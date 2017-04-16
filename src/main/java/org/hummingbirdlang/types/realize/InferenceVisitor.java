package org.hummingbirdlang.types.realize;

import org.hummingbirdlang.nodes.HBBlockNode;
import org.hummingbirdlang.nodes.HBFunctionNode;
import org.hummingbirdlang.nodes.HBSourceRootNode;

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
}
