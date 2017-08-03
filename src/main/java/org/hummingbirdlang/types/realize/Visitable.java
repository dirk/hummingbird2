package org.hummingbirdlang.types.realize;

import org.hummingbirdlang.types.scope.NameNotFoundException;

public interface Visitable {
  public void accept(InferenceVisitor visitor) throws NameNotFoundException;
}
