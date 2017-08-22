package org.hummingbirdlang.nodes;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.source.SourceSection;

import org.hummingbirdlang.HBLanguage;
import org.hummingbirdlang.nodes.frames.GetBindingsNodeGen;
import org.hummingbirdlang.nodes.frames.GetLocalNodeGen;
import org.hummingbirdlang.objects.Bindings;
import org.hummingbirdlang.objects.Function;
import org.hummingbirdlang.types.FunctionType;
import org.hummingbirdlang.types.Type;
import org.hummingbirdlang.types.TypeException;
import org.hummingbirdlang.types.realize.InferenceVisitor;
import org.hummingbirdlang.types.scope.Resolution;
import org.hummingbirdlang.types.scope.Scope;

public abstract class HBFunctionNode extends HBStatementNode {
  private final String name;
  @Child private HBFunctionRootNode rootNode;
  @CompilationFinal FunctionType functionType;

  public HBFunctionNode(HBLanguage language, String name, HBBlockNode block, SourceSection sourceSection) {
    this.name = name;
    this.rootNode = new HBFunctionRootNode(language, sourceSection, new FrameDescriptor(), block);
  }

  public void accept(InferenceVisitor visitor) throws TypeException {
    visitor.enter(this);
    this.rootNode.accept(visitor);
    visitor.leave(this);
  }

  public String getName() {
    return name;
  }

  public CallTarget getCallTarget() {
    return Truffle.getRuntime().createCallTarget(this.rootNode);
  }

  public Type getFunctionType() {
    return this.functionType;
  }

  public void setFunctionType(FunctionType functionType) {
    this.functionType = functionType;
  }

  @Specialization
  public Object cachedExecuteGeneric(
    VirtualFrame frame,
    @Cached("createFunction(frame)") Function value
  ) {
    FrameSlot frameSlot = frame.getFrameDescriptor().findOrAddFrameSlot(this.name);
    frame.setObject(frameSlot, value);
    this.rootNode.setBindings(value.getBindings());
    return null;
  }

  protected Function createFunction(VirtualFrame frame) {
    return new Function(this.functionType, this.createBindings(frame));
  }

  protected Bindings createBindings(VirtualFrame frame) {
    Scope functionScope = this.functionType.getScope();
    // Builtin functions won't have a scope.
    if (functionScope == null) {
      return null;
    }

    Bindings ownBindings = (Bindings)GetBindingsNodeGen.create().executeGeneric(frame);
    Bindings bindings = new Bindings();
    for (Resolution resolution : functionScope.getNonLocalResolutions()) {
      String name = resolution.getName();
      if (ownBindings != null && ownBindings.contains(name)) {
        bindings.put(name, ownBindings.get(name));
      } else {
        Object value = GetLocalNodeGen.create(name).executeGeneric(frame);
        bindings.put(name, value);
      }
    }
    return bindings;
  }

  @Override
  public String toString() {
    StringBuilder result = new StringBuilder("HBFunctionNode(");
    result.append(this.rootNode.toString());
    result.append(")");
    return result.toString();
  }
}
