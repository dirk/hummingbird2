package org.hummingbirdlang.types.scope;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hummingbirdlang.types.Type;

/**
 * Provides a cached implementation of `get`.
 */
abstract class AbstractScope implements Scope {
  private boolean closed = false;
  private Map<String, Resolution> cachedResolutions = new HashMap<>();

  @Override
  public Resolution resolve(String name) throws NameNotFoundException {
    Resolution resolution;
    if (this.cachedResolutions.containsKey(name)) {
      resolution = this.cachedResolutions.get(name);
    } else {
      resolution = new Resolution(name);
      this.accept(resolution);
    }
    return resolution;
  }

  @Override
  public Type get(String name) throws NameNotFoundException {
    return this.resolve(name).getType();
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

  @Override
  public List<Resolution> getNonLocalResolutions() {
    ArrayList<Resolution> nonLocalResolutions = new ArrayList<>();
    for (Resolution resolution : this.cachedResolutions.values()) {
      if (resolution.isNonLocal()) {
        nonLocalResolutions.add(resolution);
      }
    }
    return nonLocalResolutions;
  }
}
