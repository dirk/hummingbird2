package org.hummingbirdlang.nodes;

import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.source.SourceSection;

import org.hummingbirdlang.HBLanguage;
import org.hummingbirdlang.objects.Bindings;
import org.hummingbirdlang.objects.Function;
import org.hummingbirdlang.types.FunctionType;
import org.hummingbirdlang.types.Type;
import org.hummingbirdlang.types.TypeException;
import org.hummingbirdlang.types.realize.InferenceVisitable;
import org.hummingbirdlang.types.realize.InferenceVisitor;
import org.hummingbirdlang.types.scope.BuiltinScope;
import org.hummingbirdlang.types.scope.SourceScope;

public class HBSourceRootNode extends RootNode implements InferenceVisitable {
  @Child private HBBlockNode bodyNode;
  private final SourceSection sourceSection;
  @CompilationFinal private BuiltinScope builtinScope;

  public HBSourceRootNode(HBLanguage language, SourceSection sourceSection, FrameDescriptor frameDescriptor, HBBlockNode bodyNode) {
    super(language, frameDescriptor);
    this.sourceSection = sourceSection;
    this.bodyNode = bodyNode;
  }

  public void accept(InferenceVisitor visitor) throws TypeException {
    visitor.enter(this);
    this.bodyNode.accept(visitor);
    visitor.leave(this);
  }

  public void setBuiltinScope(BuiltinScope builtinScope) {
    this.builtinScope = builtinScope;
  }

  @Override
  public Object execute(VirtualFrame frame) {
    FrameDescriptor frameDescriptor = frame.getFrameDescriptor();

    try {
      Bindings bindings = new Bindings(this.builtinScope);
      FrameSlot frameSlot = frameDescriptor.findOrAddFrameSlot(Bindings.IDENTIFIER);
      frame.setObject(frameSlot, bindings);

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
