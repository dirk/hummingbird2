package org.hummingbirdlang.nodes.frames;

import org.hummingbirdlang.nodes.HBNode;
import org.hummingbirdlang.runtime.bindings.Bindings;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.VirtualFrame;

public abstract class GetBindingsNode extends HBNode {
  @Specialization
  public Bindings executeGetBindings(VirtualFrame frame) {
    FrameDescriptor frameDescriptor = frame.getFrameDescriptor();
    FrameSlot frameSlot = frameDescriptor.findOrAddFrameSlot(Bindings.IDENTIFIER);
    return (Bindings)frame.getValue(frameSlot);
  }
}
