package org.hummingbirdlang.types;

/**
 * Composite types are the intersection point between concrete types and
 * unknown types. Structurally composite types and unknown types can be mixed
 * and matched.
 */
public class CompositeType extends Type {
  // TODO: Figure out if this is semantically a `superType` or `superClass`.
  private Type superClass;
}
