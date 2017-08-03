package org.hummingbirdlang.types.scope;

import org.hummingbirdlang.types.Type;

public interface Scope {
  public Type find(String name) throws NameNotFoundException;
  public Scope getParent();
}
