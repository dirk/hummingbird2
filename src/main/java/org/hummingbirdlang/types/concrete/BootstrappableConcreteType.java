package org.hummingbirdlang.types.concrete;

import org.hummingbirdlang.nodes.builtins.BuiltinNodes;
import org.hummingbirdlang.types.ConcreteType;
import org.hummingbirdlang.types.realize.Index.Module;

public abstract class BootstrappableConcreteType extends ConcreteType {
  /**
   * Name that the type will be bootstrapped into the index module as. Should
   * be "String", "Integer", etc.
   */
  public abstract String getBootstrapName();

  /**
   * Called at the start of bootstrapping so that the type can fetch call
   * targets from the `BuiltinNodes`.
   */
  public abstract void bootstrapBuiltins(BuiltinNodes builtins);

  /**
   * Called at the end of bootstrapping so that it can fill in references
   * to other built-in types.
   */
  public abstract void bootstrapTypes(Module indexModule);
}
