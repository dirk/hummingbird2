package org.hummingbirdlang;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.source.SourceSection;

public class HBFunctionRootNode extends RootNode {
  public HBFunctionRootNode(SourceSection sourceSection, FrameDescriptor frameDescriptor) {
    super(HBLanguage.class, sourceSection, frameDescriptor);
  }

  @Override
  public Object execute(VirtualFrame frame) {
    return null;
  }
}
