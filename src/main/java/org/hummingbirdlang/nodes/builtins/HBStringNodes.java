package org.hummingbirdlang.nodes.builtins;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;

@BuiltinClass("String")
public abstract class HBStringNodes {
  @BuiltinMethod("toUpperCase")
  @NodeChild(value = "self")
  public abstract static class ToUpperCaseNode extends BuiltinNode {
    @Specialization
    public String toUpperCase(VirtualFrame frame, String self) {
      return self.toUpperCase();
    }
  }
}
