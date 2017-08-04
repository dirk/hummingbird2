package org.hummingbirdlang.types.realize;

import org.hummingbirdlang.types.TypeException;

public interface Visitable {
  public void accept(InferenceVisitor visitor)
    throws TypeException;
}
