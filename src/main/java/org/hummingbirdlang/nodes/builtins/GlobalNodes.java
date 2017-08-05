package org.hummingbirdlang.nodes.builtins;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;

import org.hummingbirdlang.nodes.HBNode;

@BuiltinClass("Global")
public abstract class GlobalNodes {
  @BuiltinMethod(value = "println", usesThis = false, required = 1)
  @NodeChild(value = "object", type = HBNode.class)
  public abstract static class PrintlnNode extends BuiltinNode {
    @Specialization
    public Object println(Object object) {
      System.out.println(object);
      return null;
    }
  }
}
