package org.hummingbirdlang.types.realize;

import org.hummingbirdlang.types.TypeException;

public interface InferenceVisitable {
  public void accept(InferenceVisitor visitor)
    throws TypeException;
}
