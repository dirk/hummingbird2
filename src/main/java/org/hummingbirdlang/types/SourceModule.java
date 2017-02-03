package org.hummingbirdlang.types;

import java.util.HashMap;
import java.util.Map;

/**
 * Source-modules are the top-level containers of types. Every source file
 * corresponds to a source-module containing types.
 */
public class SourceModule implements Scope {
  private Map<String, Type> types;

  public SourceModule() {
    this.types = new HashMap<>();
  }
}
