package org.hummingbirdlang.types.realize;

import java.util.ArrayList;
import java.util.HashMap;

import org.hummingbirdlang.nodes.builtins.BuiltinNodes;
import org.hummingbirdlang.types.FunctionType;
import org.hummingbirdlang.types.Type;
import org.hummingbirdlang.types.UnknownType;
import org.hummingbirdlang.types.concrete.BootstrappableConcreteType;
import org.hummingbirdlang.types.concrete.IntegerType;
import org.hummingbirdlang.types.concrete.NullType;
import org.hummingbirdlang.types.concrete.StringType;

/**
 * Maps fully-qualified names to types. Also reponsible for bootstrapping
 * concrete types.
 */
public final class Index {
  public static final String BUILTIN = "builtin";

  private final HashMap<String, Module> modules = new HashMap<>();

  private Index(BuiltinNodes builtins) {
    // Instantiate all the bootstrappable types.
    ArrayList<BootstrappableConcreteType> bootstrappableTypes = new ArrayList<>();
    bootstrappableTypes.add(new IntegerType());
    bootstrappableTypes.add(new StringType());

    Module builtin = new Module(Index.BUILTIN);

    // Bootstrap the types with the builtins (so that they can instantiate
    // methods with call targets).
    for (BootstrappableConcreteType type : bootstrappableTypes) {
      type.bootstrapBuiltins(builtins);
      builtin.put(type.getBootstrapName(), type);
    }

    // Add root builtin functions to the module.
    builtin.put("println", new FunctionType(new Type[]{new UnknownType()}, NullType.SINGLETON, "println", builtins.getCallTarget("Global", "println")));

    // Finalize the bootstrapped types; now that all the types are in the
    // builtin module they can now reference each other.
    for (BootstrappableConcreteType type : bootstrappableTypes) {
      type.bootstrapTypes(builtin);
    }

    this.put(builtin);
  }

  public static Index bootstrap(BuiltinNodes builtins) {
    return new Index(builtins);
  }

  public Module get(String name) {
    return this.modules.get(name);
  }

  public Module getBuiltin() {
    return this.modules.get(Index.BUILTIN);
  }

  public Module put(Module module) {
    this.modules.put(module.getName(), module);
    return module;
  }

  public final class Module {
    private final String name;
    private final HashMap<String, Type> types = new HashMap<>();

    public Module(String name) {
      this.name = name;
    }

    public String getName() {
      return this.name;
    }

    public Type get(String name) {
      if (!this.types.containsKey(name)) {
        throw new RuntimeException("Name not found in module " + this.name + ": " + name);
      }
      return this.types.get(name);
    }

    public Type put(String name, Type type) {
      return this.types.put(name, type);
    }
  }
}
