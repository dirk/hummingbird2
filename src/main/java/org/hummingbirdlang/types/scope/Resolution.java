package org.hummingbirdlang.types.scope;

import java.util.ArrayList;
import java.util.List;

import org.hummingbirdlang.types.Type;

/**
 * Resolved path to a name in a series of parent scope(s).
 */
public final class Resolution {
  private String name;
  private Type type;
  // First item is nearest parent, last item is furthest/highest parent.
  private List<Scope> path;

  public Resolution(String name) {
    this.name = name;
    this.type = null;
    this.path = new ArrayList<>();
  }

  public String getName() {
    return this.name;
  }

  public void pushScope(Scope scope) {
    this.path.add(scope);
  }

  public Type getType() {
    return this.type;
  }

  public void setType(Type type) {
    this.type = type;
  }
}
