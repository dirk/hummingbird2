package org.hummingbirdlang.nodes;

import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import org.hummingbirdlang.types.Type;
import org.hummingbirdlang.types.realize.Typeable;

@NodeInfo(language = "HB")
public abstract class HBExpressionNode extends HBStatementNode implements Typeable {
  @CompilationFinal Type type;

  public Type getType() {
    return this.type;
  }

  public void setType(Type type) {
    this.type = type;
  }
}
