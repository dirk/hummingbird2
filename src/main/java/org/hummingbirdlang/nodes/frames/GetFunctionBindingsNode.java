package org.hummingbirdlang.nodes.frames;

import org.hummingbirdlang.nodes.HBNode;
import org.hummingbirdlang.objects.Function;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;

// Extracts and returns the bindings from a function.
//
// Child node should return a `Function` when executed.
@NodeChild("node")
public abstract class GetFunctionBindingsNode extends HBNode {
  @Specialization
  public Object executeGet(VirtualFrame frame, Function function) {
    return function.getBindings();
  }
}
