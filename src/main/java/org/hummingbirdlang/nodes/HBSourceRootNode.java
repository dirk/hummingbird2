package org.hummingbirdlang.nodes;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.source.SourceSection;

import org.hummingbirdlang.HBLanguage;
import org.hummingbirdlang.types.realize.InferenceVisitor;
import org.hummingbirdlang.types.realize.Visitable;
import org.hummingbirdlang.types.scope.NameNotFoundException;

public class HBSourceRootNode extends RootNode implements Visitable {
  @Child private HBBlockNode bodyNode;
  private final SourceSection sourceSection;

  public HBSourceRootNode(HBLanguage language, SourceSection sourceSection, FrameDescriptor frameDescriptor, HBBlockNode bodyNode) {
    super(language, frameDescriptor);
    this.sourceSection = sourceSection;
    this.bodyNode = bodyNode;
  }

  public void accept(InferenceVisitor visitor) throws NameNotFoundException {
    visitor.enter(this);
    this.bodyNode.accept(visitor);
    visitor.leave(this);
  }

  @Override
  public Object execute(VirtualFrame frame) {
    try {
      this.bodyNode.executeVoid(frame);
    } catch (Exception exception) {
      throw new RuntimeException(exception);
    }
    return null;
  }

  @Override
  public SourceSection getSourceSection() {
    return this.sourceSection;
  }

  @Override
  public String toString() {
    StringBuilder result = new StringBuilder("HBSourceRootNode(");
    result.append(bodyNode.toString());
    result.append(")");
    return result.toString();
  }
}
