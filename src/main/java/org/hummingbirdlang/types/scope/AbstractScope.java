package org.hummingbirdlang.types.scope;

import java.util.HashMap;
import java.util.Map;

import org.hummingbirdlang.types.Type;

/**
 * Provides a cached implementation of `get`.
 */
abstract class AbstractScope implements Scope {
  private Map<String, Resolution> cachedResolutions = new HashMap<>();

  @Override
  public Type get(String name) throws NameNotFoundException {
    Resolution resolution;
    if (this.cachedResolutions.containsKey(name)) {
      resolution = this.cachedResolutions.get(name);
    } else {
      resolution = new Resolution(name);
      this.accept(resolution);
    }
    return resolution.getType();
  }
}
