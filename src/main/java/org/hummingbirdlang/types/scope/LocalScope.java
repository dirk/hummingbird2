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
  public Type find(String name) throws NameNotFoundException {
    if (this.types.containsKey(name)) {
      return this.types.get(name);
    } else {
      return this.parent.find(name);
    }
  }

  @Override
  public Scope getParent() {
    return this.parent;
  }
}
