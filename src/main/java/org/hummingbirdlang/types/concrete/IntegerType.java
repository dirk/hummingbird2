package org.hummingbirdlang.types.concrete;

import org.hummingbirdlang.nodes.builtins.BuiltinNodes;
import org.hummingbirdlang.types.MethodProperty;
import org.hummingbirdlang.types.Property;
import org.hummingbirdlang.types.PropertyNotFoundException;
import org.hummingbirdlang.types.UnknownType;
import org.hummingbirdlang.types.realize.Index.Module;

public final class IntegerType extends BootstrappableConcreteType {
  public static String BUILTIN_NAME = "Integer";

  private MethodType toString;

  @Override
  public String getBootstrapName() {
    return IntegerType.BUILTIN_NAME;
  }

  @Override
  public void bootstrapBuiltins(BuiltinNodes builtins) {
    this.toString = new MethodType(this, new UnknownType(), "toString", builtins.getCallTarget("Integer", "toString"));
  }

  @Override
  public void bootstrapTypes(Module indexModule) {
    this.toString = this.toString.cloneWithReturnType(indexModule.get(StringType.BUILTIN_NAME));
  }

  @Override
  public Property getProperty(String name) throws PropertyNotFoundException {
    switch (name) {
      case "toString":
        return MethodProperty.fromType(this.toString);
      default:
        throw new PropertyNotFoundException(this, name);
    }
  }

  @Override
  public String toString() {
    return "$Integer";
  }
}
