package org.hummingbirdlang.types.scope;

import org.hummingbirdlang.types.Type;

public interface Scope {
  public Type get(String name) throws NameNotFoundException;
  public void setLocal(String name, Type type);
  public Scope getParent();
}
