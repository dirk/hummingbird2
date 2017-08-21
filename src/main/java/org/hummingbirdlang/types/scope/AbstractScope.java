package org.hummingbirdlang.types.scope;

import java.util.HashMap;
import java.util.Map;

import org.hummingbirdlang.types.Type;

/**
 * Provides a cached implementation of `get`.
 */
abstract class AbstractScope implements Scope {
  private boolean closed = false;
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

  @Override
  public void close() {
    if (this.closed) {
      throw new RuntimeException("Scope already closed");
    }
    this.closed = true;
  }

  @Override
  public boolean isClosed() {
    return this.closed;
  }

  protected void assertIsNotClosed() {
    if (this.closed) {
      throw new RuntimeException("Scope is closed");
    }
  }
}
