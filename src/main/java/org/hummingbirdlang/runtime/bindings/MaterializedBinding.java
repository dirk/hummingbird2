package org.hummingbirdlang.runtime.bindings;

import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.MaterializedFrame;

public final class MaterializedBinding implements Binding {
  private MaterializedFrame frame;
  private String name;

  public MaterializedBinding(String name, MaterializedFrame frame) {
    this.frame = frame;
    this.name = name;
  }

  @Override
  public Object get() {
    FrameSlot frameSlot = this.frame.getFrameDescriptor().findFrameSlot(this.name);
    return frame.getValue(frameSlot);
  }
}
