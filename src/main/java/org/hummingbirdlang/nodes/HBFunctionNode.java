package org.hummingbirdlang.nodes;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.source.SourceSection;

import org.hummingbirdlang.types.realize.InferenceVisitor;

public class HBFunctionNode extends HBStatementNode {
  private final String name;
  @Child private HBFunctionRootNode rootNode;

  public HBFunctionNode(String name, HBBlockNode block, SourceSection sourceSection) {
    this.name = name;
    this.rootNode = new HBFunctionRootNode(null, sourceSection, new FrameDescriptor(), block);
  }

  public void accept(InferenceVisitor visitor) {
    visitor.enter(this);
    this.rootNode.accept(visitor);
    visitor.leave(this);
  }

  @Override
  public void executeVoid(VirtualFrame frame) {
    return;
  }
}
