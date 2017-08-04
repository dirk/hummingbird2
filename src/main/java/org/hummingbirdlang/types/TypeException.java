package org.hummingbirdlang.types;

public abstract class TypeException extends Exception {
  protected TypeException(String message) {
    super(message);
  }
}
