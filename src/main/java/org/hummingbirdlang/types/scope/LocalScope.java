package org.hummingbirdlang.types.scope;

import java.util.HashMap;
import java.util.Map;

import org.hummingbirdlang.types.Type;

/**
 * Defines a local scope, such as inside of a function.
 */
public class LocalScope implements Scope {
  private Scope parent;
  private Map<String, Type> types;

  public LocalScope(Scope parent) {
    this.parent = parent;
    this.types = new HashMap<>();
  }

  @Override
  public Type get(String name) throws NameNotFoundException {
    if (this.types.containsKey(name)) {
      return this.types.get(name);
    } else {
      return this.parent.get(name);
    }
  }

  @Override
  public void setLocal(String name, Type type) {
    this.types.put(name, type);
  }

  @Override
  public Scope getParent() {
    return this.parent;
  }
}
