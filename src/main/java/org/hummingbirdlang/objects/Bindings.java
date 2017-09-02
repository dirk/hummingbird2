package org.hummingbirdlang.objects;

import java.util.HashMap;
import java.util.Map;

import org.hummingbirdlang.types.FunctionType;
import org.hummingbirdlang.types.Type;
import org.hummingbirdlang.types.scope.BuiltinScope;
import org.hummingbirdlang.types.scope.NameNotFoundException;

// Holds references to values closed from an outer scope.
public final class Bindings {
  private Map<String, Binding> bindings;

  public Bindings() {
    this.bindings = new HashMap<>();
  }

  public Bindings(BuiltinScope builtinScope) throws NameNotFoundException {
    this();
    // Bootstrap builtins into frame.
    for (String name : builtinScope) {
      Type type = builtinScope.get(name);
      if (type instanceof FunctionType) {
        Object function = new Function((FunctionType)type, null);
        this.bindings.put(name, new BuiltinBinding(name, function));
      } else {
        System.out.println("Skipping bootstrap of builtin " + name + ": " + type.toString());
      }
    }
  }

  public boolean contains(String name) {
    return this.bindings.containsKey(name);
  }

  public Binding get(String name) {
    return this.bindings.get(name);
  }

  public Object getValue(String name) {
    Binding binding = this.get(name);
    return binding.get();
  }

  public void add(String name, Binding binding) {
    this.bindings.put(name, binding);
  }

  public static final BindingsIdentifier IDENTIFIER = new BindingsIdentifier();

  private static final class BindingsIdentifier {
    private BindingsIdentifier() {
    }
  }
}
