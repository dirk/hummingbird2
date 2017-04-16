package org.hummingbirdlang.types.realize;

public interface Visitable {
  public void accept(InferenceVisitor visitor);
}
