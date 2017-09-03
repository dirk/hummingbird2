package org.hummingbirdlang.types;

public class TypeMismatchException extends TypeException {
  public TypeMismatchException(String message) {
    super(message);
  }

  public TypeMismatchException(Type leftType, Type rightType) {
    super("Type " + String.valueOf(leftType) + " does not match " + String.valueOf(rightType));
  }
}
