package org.hummingbirdlang.nodes.builtins;

import org.hummingbirdlang.nodes.HBNode;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;

@BuiltinClass("String")
public abstract class HBStringNodes {
  @BuiltinMethod("toUpperCase")
  @NodeChild(value = "self", type = HBNode.class)
  public abstract static class ToUpperCaseNode extends BuiltinNode {
    @Specialization
    public String toUpperCase(String self) {
      return self.toUpperCase();
    }
  }
}
