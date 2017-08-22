package org.hummingbirdlang.nodes.frames;

import org.hummingbirdlang.nodes.HBNode;
import org.hummingbirdlang.objects.Bindings;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.VirtualFrame;

// Child node should return bindings when executed.
@NodeChild("node")
public abstract class SetBindingsNode extends HBNode {
  @Specialization
  public Object executeSet(VirtualFrame frame, Bindings bindings) {
    FrameDescriptor frameDescriptor = frame.getFrameDescriptor();
    FrameSlot frameSlot = frameDescriptor.findOrAddFrameSlot(Bindings.IDENTIFIER);
    frame.setObject(frameSlot, bindings);
    return null;
  }
}
