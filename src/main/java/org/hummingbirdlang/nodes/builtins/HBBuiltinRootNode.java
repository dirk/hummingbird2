package org.hummingbirdlang.nodes.builtins;

import org.hummingbirdlang.HBLanguage;
import org.hummingbirdlang.nodes.HBNode;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.nodes.Node.Child;

public class HBBuiltinRootNode extends RootNode {
  @Child private HBNode bodyNode;

  public HBBuiltinRootNode(HBLanguage language, FrameDescriptor frameDescriptor, HBNode bodyNode) {
    super(language, frameDescriptor);
    this.bodyNode = bodyNode;
  }

  @Override
  public Object execute(VirtualFrame frame) {
    return this.bodyNode.executeGeneric(frame);
  }
}
