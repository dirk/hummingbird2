package org.hummingbirdlang.nodes;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.source.SourceSection;

import org.hummingbirdlang.HBLanguage;
import org.hummingbirdlang.types.realize.InferenceVisitor;
import org.hummingbirdlang.types.realize.Visitable;

public class HBFunctionRootNode extends RootNode implements Visitable {
  @Child private HBBlockNode bodyNode;

  public HBFunctionRootNode(HBBlockNode bodyNode, SourceSection sourceSection, FrameDescriptor frameDescriptor) {
    super(HBLanguage.class, sourceSection, frameDescriptor);
    this.bodyNode = bodyNode;
  }

  public void accept(InferenceVisitor visitor) {
    this.bodyNode.accept(visitor);
  }

  @Override
  public Object execute(VirtualFrame frame) {
    return null;
  }
}
