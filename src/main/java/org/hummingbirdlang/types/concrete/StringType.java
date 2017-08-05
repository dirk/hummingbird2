package org.hummingbirdlang.types.concrete;

import org.hummingbirdlang.nodes.builtins.BuiltinNodes;
import org.hummingbirdlang.types.ConcreteType;
import org.hummingbirdlang.types.MethodProperty;
import org.hummingbirdlang.types.Property;
import org.hummingbirdlang.types.PropertyNotFoundException;

public final class StringType extends ConcreteType {
  private final MethodType toUpperCase;

  private StringType(BuiltinNodes builtins) {
    this.toUpperCase = new MethodType(this, "toUpperCase", builtins.get("String").get("toUpperCase"));
  }

  public static StringType bootstrap(BuiltinNodes builtins) {
    return new StringType(builtins);
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
