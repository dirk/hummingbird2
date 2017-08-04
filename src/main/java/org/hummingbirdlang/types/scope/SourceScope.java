package org.hummingbirdlang.types.scope;

import java.util.HashMap;
import java.util.Map;

import org.hummingbirdlang.types.Type;

/**
 * Source files are the top-level containers of types. Every source file
 * corresponds to a source scope containing names referencing types.
 */
public class SourceScope implements Scope {
  private Map<String, Type> types;

  public SourceScope() {
    this.types = new HashMap<>();
  }

  @Override
  public Type get(String name) throws NameNotFoundException {
    throw new NameNotFoundException("Name not found in SourceScope: " + name);
  }

  @Override
  public void setLocal(String name, Type type) {
    this.types.put(name, type);
  }

  @Override
  public Scope getParent() {
    throw new RuntimeException("Cannot getParent of SourceScope");
  }
}
