package org.hummingbirdlang.nodes.builtins;

import org.hummingbirdlang.nodes.HBNode;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;

@BuiltinClass("Integer")
public abstract class IntegerNodes {
  @BuiltinMethod("toString")
  @NodeChild(value = "self", type = HBNode.class)
  public abstract static class ToStringNode extends BuiltinNode {
    @Specialization
    public String intToString(int self) {
      return String.valueOf(self);
    }

    @Specialization
    public String longToString(long self) {
      return String.valueOf(self);
    }
  }
}
