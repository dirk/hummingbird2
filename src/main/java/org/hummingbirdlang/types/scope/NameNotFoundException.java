package org.hummingbirdlang.types.scope;

import org.hummingbirdlang.types.TypeException;

public class NameNotFoundException extends TypeException {
  public NameNotFoundException(String message) {
    super(message);
  }
}
