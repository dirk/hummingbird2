package org.hummingbirdlang;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.source.SourceSection;

public class HBSourceRootNode extends RootNode {
  @Child private HBBlockNode bodyNode;

  public HBSourceRootNode(SourceSection sourceSection, FrameDescriptor frameDescriptor, HBBlockNode bodyNode) {
    super(HBLanguage.class, sourceSection, frameDescriptor);
    this.bodyNode = bodyNode;
  }

  @Override
  public Object execute(VirtualFrame frame) {
    return null;
  }

  @Override
  public String toString() {
    StringBuilder result = new StringBuilder("HBSourceRootNode(");
    result.append(bodyNode.toString());
    result.append(")");
    return result.toString();
  }
}
