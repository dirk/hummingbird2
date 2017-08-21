package org.hummingbirdlang.objects;

import java.util.HashMap;
import java.util.Map;

import org.hummingbirdlang.types.FunctionType;
import org.hummingbirdlang.types.Type;
import org.hummingbirdlang.types.scope.BuiltinScope;
import org.hummingbirdlang.types.scope.NameNotFoundException;

// Holds references to values closed from an outer scope.
public final class Bindings {
  private Map<String, Object> bindings;

  public Bindings() {
    this.bindings = new HashMap<>();
  }

  public Bindings(BuiltinScope builtinScope) throws NameNotFoundException {
    this();
    // Bootstrap builtins into frame.
    for (String name : builtinScope) {
      Type type = builtinScope.get(name);
      if (type instanceof FunctionType) {
        Object function = new Function((FunctionType)type);
        this.bindings.put(name, function);
      } else {
        System.out.println("Skipping bootstrap of builtin " + name + ": " + type.toString());
      }
    }
  }

  public Object get(String name) {
    return this.bindings.get(name);
  }

  public static final BindingsIdentifier IDENTIFIER = new BindingsIdentifier();

  private static final class BindingsIdentifier {
    private BindingsIdentifier() {
    }
  }
}
