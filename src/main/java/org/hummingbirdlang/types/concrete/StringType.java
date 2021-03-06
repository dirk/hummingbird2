package org.hummingbirdlang.types.concrete;

import org.hummingbirdlang.nodes.builtins.BuiltinNodes;
import org.hummingbirdlang.nodes.builtins.StringNodes;
import org.hummingbirdlang.types.MethodProperty;
import org.hummingbirdlang.types.Property;
import org.hummingbirdlang.types.PropertyNotFoundException;
import org.hummingbirdlang.types.Type;
import org.hummingbirdlang.types.realize.Index.Module;

public final class StringType extends BootstrappableConcreteType {
  public static String BUILTIN_NAME = "String";

  private MethodType toUpperCase;

  @Override
  public String getBootstrapName() {
    return StringType.BUILTIN_NAME;
  }

  @Override
  public void bootstrapBuiltins(BuiltinNodes builtins) {
    this.toUpperCase = builtins.createMethodType(this, new Type[]{}, this, StringNodes.ToUpperCaseNode.class);
  }

  @Override
  public void bootstrapTypes(Module builtinModule) {
    return;
  }

  @Override
  public Property getProperty(String name) throws PropertyNotFoundException {
    switch (name) {
      case "toUpperCase":
        return MethodProperty.fromType(this.toUpperCase);
      default:
        throw new PropertyNotFoundException(this, name);
    }
  }

  @Override
  public String toString() {
    // NOTE: Concrete types' string representations are prefixed with "$"
    //   to indicate their internal, concrete nature (for now).
    return "$String";
  }
}
