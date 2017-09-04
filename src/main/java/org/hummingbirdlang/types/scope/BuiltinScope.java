package org.hummingbirdlang.types.scope;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hummingbirdlang.types.Type;
import org.hummingbirdlang.types.composite.TypeReferenceType;
import org.hummingbirdlang.types.concrete.IntegerType;
import org.hummingbirdlang.types.concrete.StringType;
import org.hummingbirdlang.types.realize.Index;

public class BuiltinScope extends AbstractScope implements Scope, Iterable<String> {
  private final Map<String, Type> types;

  public BuiltinScope(Index index) {
    Map<String, Type> types = new HashMap<>();
    // Bootstrap the global types.
    Index.Module builtin = index.getBuiltin();
    for (String name : builtin) {
      types.put(name, builtin.get(name));
    }
    this.types = types;
    // You can't modify the builtin scope!
    this.close();
  }

  @Override
  public void accept(Resolution resolution) throws NameNotFoundException {
    resolution.pushScope(this);
    if (this.types.containsKey(resolution.getName())) {
      resolution.setType(this.types.get(resolution.getName()));
    } else {
      throw new NameNotFoundException("Name not found in BuiltinScope: " + resolution.getName());
    }
  }

  @Override
  public void setLocal(String name, Type type) {
    throw new RuntimeException("Cannot modify BuiltinScope: " + name);
  }

  @Override
  public Scope getParent() {
    throw new RuntimeException("Cannot getParent of BuiltinScope");
  }

  @Override
  public List<Resolution> getNonLocalResolutions() {
    throw new RuntimeException("Cannot getNonLocalResolutions of BuiltinScope");
  }

  @Override
  public Iterator<String> iterator() {
    return this.types.keySet().iterator();
  }
}
