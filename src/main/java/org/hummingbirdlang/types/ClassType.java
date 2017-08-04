package org.hummingbirdlang.types;

public class ClassType extends CompositeType {
  private String name;
  private Type superClass;

  public ClassType(String name, Type superClass) {
    this.name = name;
    this.superClass = superClass;
  }

  @Override
  public Property getProperty(String name) throws PropertyNotFoundException {
    throw new PropertyNotFoundException("Not yet implemented");
  }
}
