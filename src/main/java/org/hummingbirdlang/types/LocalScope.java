package org.hummingbirdlang.types;

import java.util.HashMap;
import java.util.Map;

/**
 * Defines a local scope, such as inside of a function.
 */
public class LocalScope implements Scope {
  private Map<String, Type> types;

  public LocalScope() {
    this.types = new HashMap<>();
  }
}
