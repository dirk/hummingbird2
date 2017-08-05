package org.hummingbirdlang.types.scope;

import java.util.HashMap;
import java.util.Map;

import org.hummingbirdlang.types.Type;
import org.hummingbirdlang.types.realize.Index;

/**
 * Source files are the top-level containers of types. Every source file
 * corresponds to a source scope containing names referencing types.
 */
public class SourceScope implements Scope {
  private final BuiltinScope parent;
  private Map<String, Type> types;

  public SourceScope(BuiltinScope parent) {
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
