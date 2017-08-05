package org.hummingbirdlang.nodes.builtins;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;

import org.hummingbirdlang.nodes.HBNode;

@BuiltinClass("String")
public abstract class StringNodes {
  @BuiltinMethod("toUpperCase")
  @NodeChild(value = "self", type = HBNode.class)
  public abstract static class ToUpperCaseNode extends BuiltinNode {
    @Specialization
    public String toUpperCase(String self) {
      return self.toUpperCase();
    }
  }
}
