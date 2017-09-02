package org.hummingbirdlang.runtime.bindings;

final class BuiltinBinding implements Binding {
  private String name;
  private Object value;

  BuiltinBinding(String name, Object value) {
    this.name = name;
    this.value = value;
  }

  @Override
  public Object get() {
    return this.value;
  }
}
