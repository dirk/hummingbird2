package org.hummingbirdlang.nodes;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.source.SourceSection;

import org.hummingbirdlang.HBLanguage;
import org.hummingbirdlang.types.realize.InferenceVisitor;
import org.hummingbirdlang.types.realize.Visitable;

public class HBSourceRootNode extends RootNode implements Visitable {
  @Child private HBBlockNode bodyNode;

  public HBSourceRootNode(SourceSection sourceSection, FrameDescriptor frameDescriptor, HBBlockNode bodyNode) {
    super(HBLanguage.class, sourceSection, frameDescriptor);
    this.bodyNode = bodyNode;
  }

  public void accept(InferenceVisitor visitor) {
    visitor.visit(this);
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
