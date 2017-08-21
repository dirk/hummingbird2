package org.hummingbirdlang.nodes;

import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node.Child;

import org.hummingbirdlang.nodes.frames.GetLocalNodeGen;
import org.hummingbirdlang.nodes.frames.GetNonLocalNodeGen;
import org.hummingbirdlang.parser.Token;
import org.hummingbirdlang.types.realize.InferenceVisitor;
import org.hummingbirdlang.types.scope.NameNotFoundException;
import org.hummingbirdlang.types.scope.Resolution;

// Basic identifier of a local or global name.
public class HBIdentifierNode extends HBExpressionNode {
  private final String name;
  @CompilationFinal Resolution resolution;
  // Node to execute to access the value. This is generated at runtime.
  @Child private HBNode accessNode;

  public HBIdentifierNode(Token token) {
    this.name = token.val;
  }

  public void accept(InferenceVisitor visitor) throws NameNotFoundException {
    visitor.visit(this);
  }

  public String getName() {
    return this.name;
  }

  public void setResolution(Resolution resolution) {
    this.resolution = resolution;
    this.setType(resolution.getType());
  }

  @Override
  public Object executeGeneric(VirtualFrame frame) {
    if (this.accessNode == null) {
      if (resolution.isLocal()) {
        this.accessNode = GetLocalNodeGen.create(this.name);
      } else {
        this.accessNode = GetNonLocalNodeGen.create(this.name);
      }
    }
    return this.accessNode.executeGeneric(frame);
  }

  @Override
  public String toString() {
    return "HBIdentifierNode(" + this.name + ")";
  }
}
