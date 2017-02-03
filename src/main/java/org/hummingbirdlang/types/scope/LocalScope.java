package org.hummingbirdlang.types.scope;

import java.util.HashMap;
import java.util.Map;

import org.hummingbirdlang.types.Type;

/**
 * Defines a local scope, such as inside of a function.
 */
public class LocalScope implements Scope {
  private Map<String, Type> types;

  public LocalScope() {
    this.types = new HashMap<>();
  }
}
