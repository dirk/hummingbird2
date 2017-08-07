package org.hummingbirdlang.types.scope;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.hummingbirdlang.types.Type;
import org.hummingbirdlang.types.composite.TypeReferenceType;
import org.hummingbirdlang.types.realize.Index;

public class BuiltinScope implements Scope, Iterable<String> {
  private final Map<String, Type> types;

  public BuiltinScope(Index index) {
    Map<String, Type> types = new HashMap<>();
    // Bootstrap the global types.
    Index.Module builtin = index.getBuiltin();
    types.put("String", new TypeReferenceType(builtin.get("StringType")));
    types.put("println", builtin.get("println"));
    this.types = types;
  }

  @Override
  public Type get(String name) throws NameNotFoundException {
    if (this.types.containsKey(name)) {
      return this.types.get(name);
    } else {
      throw new NameNotFoundException("Name not found in BuiltinScope: " + name);
    }
  }

  @Override
  public void setLocal(String name, Type type) {
    throw new Error("Cannot modify BuiltinScope: " + name);
  }

  @Override
  public Scope getParent() {
    throw new Error("Cannot getParent of BuiltinScope");
  }

  @Override
  public Iterator<String> iterator() {
    return this.types.keySet().iterator();
  }
}