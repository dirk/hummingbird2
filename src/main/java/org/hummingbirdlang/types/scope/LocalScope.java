package org.hummingbirdlang.types.scope;

import java.util.HashMap;
import java.util.Map;

import org.hummingbirdlang.types.Type;

/**
 * Defines a local scope, such as inside of a function.
 */
public class LocalScope extends AbstractScope implements Scope {
  private Scope parent;
  // Types of local variables.
  private Map<String, Type> types;

  public LocalScope(Scope parent) {
    this.parent = parent;
    this.types = new HashMap<>();
  }

  @Override
  public void accept(Resolution resolution) throws NameNotFoundException {
    resolution.pushScope(this);
    if (this.types.containsKey(resolution.getName())) {
      resolution.setType(this.types.get(resolution.getName()));
    } else {
      this.parent.accept(resolution);
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
