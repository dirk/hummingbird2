package org.hummingbirdlang.nodes;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.source.SourceSection;

import org.hummingbirdlang.HBLanguage;

public class HBFunctionRootNode extends RootNode {
  @Child private HBBlockNode bodyNode;

  public HBFunctionRootNode(HBBlockNode bodyNode, SourceSection sourceSection, FrameDescriptor frameDescriptor) {
    super(HBLanguage.class, sourceSection, frameDescriptor);
    this.bodyNode = bodyNode;
  }

  @Override
  public Object execute(VirtualFrame frame) {
    return null;
  }
}
