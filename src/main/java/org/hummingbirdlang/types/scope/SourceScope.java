package org.hummingbirdlang.types.scope;

import java.util.HashMap;
import java.util.Map;

import org.hummingbirdlang.types.Type;

/**
 * Source files are the top-level containers of types. Every source file
 * corresponds to a source scope containing names referencing types.
 */
public class SourceScope implements Scope {
  private Map<String, Type> types;

  public SourceScope() {
    this.types = new HashMap<>();
  }
}
