package org.hummingbirdlang.types;

public class FunctionType extends ConcreteType {
  private Object[] argumentTypeDeclarations;
  private Object returnTypeDeclaration;

  private Type[] argumentTypes;
  private Type returnType;

  public FunctionType(
    Object[] argumentTypeDeclarations,
    Object returnTypeDeclaration
  ) {
    this.argumentTypeDeclarations = argumentTypeDeclarations;
    this.returnTypeDeclaration = returnTypeDeclaration;

    this.argumentTypes = null;
    this.returnType = null;
  }
}
