package org.hummingbirdlang.types.composite;

import org.hummingbirdlang.types.CompositeType;
import org.hummingbirdlang.types.Property;
import org.hummingbirdlang.types.PropertyNotFoundException;
import org.hummingbirdlang.types.Type;

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
