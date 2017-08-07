package org.hummingbirdlang.nodes;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.source.SourceSection;

import org.hummingbirdlang.HBLanguage;
import org.hummingbirdlang.objects.Function;
import org.hummingbirdlang.types.FunctionType;
import org.hummingbirdlang.types.Type;
import org.hummingbirdlang.types.TypeException;
import org.hummingbirdlang.types.realize.InferenceVisitor;
import org.hummingbirdlang.types.realize.Typeable;

public class HBFunctionNode extends HBStatementNode {
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

  // Can't be `getRootNode` because Truffle's `Node` already has a final
  // implementation of it.
  public HBFunctionRootNode getOwnRootNode() {
    return this.rootNode;
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

  @Override
  public Object executeGeneric(VirtualFrame frame) {
    Function value = new Function(this.functionType);
    FrameSlot frameSlot = frame.getFrameDescriptor().findOrAddFrameSlot(this.name);
    frame.setObject(frameSlot, value);
    return null;
  }

  @Override
  public String toString() {
    StringBuilder result = new StringBuilder("HBFunctionNode(");
    result.append(this.rootNode.toString());
    result.append(")");
    return result.toString();
  }
}
