package org.hummingbirdlang.types.scope;

import java.util.HashMap;
import java.util.Map;

import org.hummingbirdlang.types.Type;
import org.hummingbirdlang.types.realize.Index;

/**
 * Source files are the top-level containers of types. Every source file
 * corresponds to a source scope containing names referencing types.
 */
public class SourceScope extends AbstractScope implements Scope {
  private final BuiltinScope parent;
  private Map<String, Type> types;

  public SourceScope(BuiltinScope parent) {
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
    this.assertIsNotClosed();
    this.types.put(name, type);
  }

  @Override
  public Scope getParent() {
    return this.parent;
  }
}
