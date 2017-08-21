package org.hummingbirdlang.nodes.frames;

import org.hummingbirdlang.nodes.HBNode;

import com.oracle.truffle.api.dsl.NodeField;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.VirtualFrame;

@NodeField(name = "name", type = String.class)
public abstract class GetLocalNode extends HBNode {
  protected abstract String getName();

  @Specialization
  public Object getObject(VirtualFrame frame) {
    FrameSlot frameSlot = frame.getFrameDescriptor().findOrAddFrameSlot(this.getName());
    return frame.getValue(frameSlot);
  }
}
