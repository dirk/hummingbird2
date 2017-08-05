package org.hummingbirdlang.types.realize;

import java.util.HashMap;

import org.hummingbirdlang.HBLanguage;
import org.hummingbirdlang.builtins.Builtins;
import org.hummingbirdlang.types.Type;
import org.hummingbirdlang.types.concrete.StringType;

/**
 * Maps fully-qualified names to types. Also reponsible for bootstrapping
 * concrete types.
 */
public final class Index {
  public static final String BUILTIN = "builtin";

  private final HashMap<String, Module> modules = new HashMap<>();

  private Index(Builtins builtins) {
    Module builtin = new Module(Index.BUILTIN);
    builtin.put("String", StringType.bootstrap(builtins));
    this.put(builtin);
  }

  public static Index bootstrap(Builtins builtins) {
    return new Index(builtins);
  }

  public Module get(String name) {
    return this.modules.get(name);
  }

  public Module getBuiltin() {
    return this.modules.get(Index.BUILTIN);
  }

  public Module put(Module module) {
    return this.modules.put(module.getName(), module);
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
      return this.types.get(name);
    }

    public Type put(String name, Type type) {
      return this.types.put(name, type);
    }
  }
}
