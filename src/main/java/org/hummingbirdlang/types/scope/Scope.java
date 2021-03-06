package org.hummingbirdlang.types.scope;

import java.util.List;

import org.hummingbirdlang.types.Type;

public interface Scope {
  // Loose visitor pattern: scopes accept visitors; if they have the name the
  // resolution is looking for then they push themselves onto the path and
  // assign the type, otherwise they push themselves onto the path and
  // recurse to their parent scope.
  public void accept(Resolution resolution) throws NameNotFoundException;

  public Resolution resolve(String name) throws NameNotFoundException;

  // Get the type for the given name. Should create a `Resolution` and call
  // the `accept` recursion under the hood.
  public Type get(String name) throws NameNotFoundException;

  public void setLocal(String name, Type type);

  public Scope getParent();

  // Close the scope to prevent further modifications.
  public void close();

  // Returns whether or not the scope has been closed.
  public boolean isClosed();

  // Should only be called on closed scopes.
  public List<Resolution> getNonLocalResolutions();
}
