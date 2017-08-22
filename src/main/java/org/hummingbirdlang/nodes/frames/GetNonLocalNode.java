package org.hummingbirdlang.nodes.frames;

import org.hummingbirdlang.nodes.HBNode;
import org.hummingbirdlang.objects.Bindings;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.NodeField;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;

@NodeField(name = "name", type = String.class)
public abstract class GetNonLocalNode extends HBNode {
  protected abstract String getName();

  @Specialization
  public Object getObject(
    VirtualFrame frame,
    @Cached("createGetBindingsNode()") GetBindingsNode getBindingsNode
  ) {
    Bindings bindings = (Bindings)getBindingsNode.executeGeneric(frame);
    if (bindings == null) {
      throw new RuntimeException("Missing bindings");
    }
    return bindings.get(this.getName());
  }

  protected GetBindingsNode createGetBindingsNode() {
    return GetBindingsNodeGen.create();
  }
}
