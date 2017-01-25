package org.hummingbirdlang;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.source.SourceSection;

@NodeInfo(language = "HB")
public class HBRootNode extends RootNode {
  public HBRootNode(SourceSection sourceSection, FrameDescriptor frameDescriptor) {
    super(HBLanguage.class, sourceSection, frameDescriptor);
  }

  @Override
  public Object execute(VirtualFrame frame) {
    return null;
  }
}
