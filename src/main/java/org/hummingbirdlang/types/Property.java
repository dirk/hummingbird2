package org.hummingbirdlang.types;

public abstract class Property {
  // The type of the thing of which this is a property. For example in
  // `foo.bar` where `foo: Foo`, the parent would be the type `Foo`.
  private final Type parent;
  // The name of the property, in the previous example `bar`.
  private final String name;

  protected Property(Type parent, String name) {
    this.parent = parent;
    this.name = name;
  }
}
