package org.hummingbirdlang.nodes;

import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.source.SourceSection;

import org.hummingbirdlang.HBLanguage;
import org.hummingbirdlang.objects.Function;
import org.hummingbirdlang.types.FunctionType;
import org.hummingbirdlang.types.Type;
import org.hummingbirdlang.types.TypeException;
import org.hummingbirdlang.types.realize.InferenceVisitor;
import org.hummingbirdlang.types.realize.Visitable;
import org.hummingbirdlang.types.scope.BuiltinScope;
import org.hummingbirdlang.types.scope.SourceScope;

public class HBSourceRootNode extends RootNode implements Visitable {
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
      // Bootstrap builtins into frame.
      for (String name : this.builtinScope) {
        Type type = this.builtinScope.get(name);
        if (type instanceof FunctionType) {
          Object function = new Function((FunctionType)type);
          FrameSlot frameSlot = frameDescriptor.findOrAddFrameSlot(name);
          frame.setObject(frameSlot, function);
        } else {
          System.out.println("Skipping bootstrap of builtin " + name + ": " + type.toString());
        }
      }

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
