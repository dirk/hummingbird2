package org.hummingbirdlang.nodes;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.source.SourceSection;

import org.hummingbirdlang.HBLanguage;
import org.hummingbirdlang.types.TypeException;
import org.hummingbirdlang.types.realize.InferenceVisitor;

public class HBFunctionNode extends HBStatementNode {
  private final String name;
  @Child private HBFunctionRootNode rootNode;

  public HBFunctionNode(HBLanguage language, String name, HBBlockNode block, SourceSection sourceSection) {
    this.name = name;
    this.rootNode = new HBFunctionRootNode(language, sourceSection, new FrameDescriptor(), block);
  }

  public void accept(InferenceVisitor visitor) throws TypeException {
    visitor.enter(this);
    this.rootNode.accept(visitor);
    visitor.leave(this);
  }

  @Override
  public Object executeGeneric(VirtualFrame frame) {
    return null;
  }

  @Override
  public String toString() {
    StringBuilder result = new StringBuilder("HBFunctionNode(");
    result.append(this.rootNode.toString());
    result.append(")");
    return result.toString();
  }
}
