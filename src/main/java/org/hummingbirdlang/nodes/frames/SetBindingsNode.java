package org.hummingbirdlang.nodes.frames;

import org.hummingbirdlang.nodes.HBNode;
import org.hummingbirdlang.objects.Bindings;

import com.oracle.truffle.api.dsl.NodeField;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.VirtualFrame;

@NodeField(name = "bindings", type = Bindings.class)
public abstract class SetBindingsNode extends HBNode {
  protected abstract Bindings getBindings();

  @Specialization
  public Object set(VirtualFrame frame) {
    FrameDescriptor frameDescriptor = frame.getFrameDescriptor();
    FrameSlot frameSlot = frameDescriptor.findOrAddFrameSlot(Bindings.IDENTIFIER);
    frame.setObject(frameSlot, this.getBindings());
    return null;
  }
}
