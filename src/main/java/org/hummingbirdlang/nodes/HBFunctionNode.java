package org.hummingbirdlang;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.source.SourceSection;

public class HBFunctionNode extends HBStatementNode {
  private final String name;
  @Child private HBFunctionRootNode rootNode;

  public HBFunctionNode(String name, HBBlockNode block, SourceSection sourceSection) {
    this.name = name;
    this.rootNode = new HBFunctionRootNode(block, sourceSection, new FrameDescriptor());
  }

  @Override
  public void executeVoid(VirtualFrame frame) {
    return;
  }
}
