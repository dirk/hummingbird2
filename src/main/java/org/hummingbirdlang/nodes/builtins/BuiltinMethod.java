package org.hummingbirdlang.nodes.builtins;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface BuiltinMethod {
  String value();

  // If it has a first argument with its receiver, ie. `this`.
  boolean usesThis() default true;
}
