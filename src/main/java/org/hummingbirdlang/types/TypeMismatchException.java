package org.hummingbirdlang.types;

public class TypeMismatchException extends TypeException {
  public TypeMismatchException(String message) {
    super(message);
  }

  public TypeMismatchException(Type leftType, Type rightType) {
    super("Type " + leftType.toString() + " does not match " + rightType.toString());
  }
}
