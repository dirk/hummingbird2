package org.hummingbirdlang.nodes;

import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.source.SourceSection;

import org.hummingbirdlang.HBLanguage;
import org.hummingbirdlang.nodes.arguments.GetTargetNode;
import org.hummingbirdlang.nodes.frames.GetFunctionBindingsNode;
import org.hummingbirdlang.nodes.frames.GetFunctionBindingsNodeGen;
import org.hummingbirdlang.nodes.frames.SetBindingsNode;
import org.hummingbirdlang.nodes.frames.SetBindingsNodeGen;
import org.hummingbirdlang.objects.Bindings;
import org.hummingbirdlang.types.TypeException;
import org.hummingbirdlang.types.realize.InferenceVisitable;
import org.hummingbirdlang.types.realize.InferenceVisitor;

public class HBFunctionRootNode extends RootNode implements InferenceVisitable {
  @Child private HBBlockNode bodyNode;
  private final SourceSection sourceSection;

  public HBFunctionRootNode(HBLanguage language, SourceSection sourceSection, FrameDescriptor frameDescriptor, HBBlockNode bodyNode) {
    super(language, frameDescriptor);
    this.sourceSection = sourceSection;
    this.bodyNode = bodyNode;
  }

  public void accept(InferenceVisitor visitor) throws TypeException {
    this.bodyNode.accept(visitor);
  }

  @Override
  public SourceSection getSourceSection() {
    return this.sourceSection;
  }

  @Override
  public Object execute(VirtualFrame frame) {
    try {
      createBindingsGetterAndSetterNodes().executeGeneric(frame);
      this.bodyNode.executeGeneric(frame);
    } catch (HBReturnException exception) {
      return exception.getReturnValue();
    }
    return null;
  }

  private SetBindingsNode createBindingsGetterAndSetterNodes() {
    return (
      SetBindingsNodeGen.create(
        GetFunctionBindingsNodeGen.create(
          new GetTargetNode()
        )
      )
    );
  }

  @Override
  public String toString() {
    return this.bodyNode.toString();
  }
}
