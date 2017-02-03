package org.hummingbirdlang.types;

/**
 * Root of all representations and derivations of types.
 *
 * There are three key types in the type-system:
 *
 * 1. Concrete types: The fundamental types provided by the language.
 *    Contains both value (eg. Int32) and reference (eg. Object) types.
 *
 * 2. Composite types: Built-in and user-defined types which can both:
 *    derive (subclass) concrete types and/or compose (tuples & generics)
 *    other types. There are a couple structures of composite types:
 *       - Classes (see ClassType)
 *       - Tuples (see TupleType)
 *
 * 3. Unknown types: This is how the language supports ad-hoc and parametric
 *    polymorphism. The type-checker and/or run-time will eventually realize
 *    these into concrete or composite types.
 *
 * The general idea is that any code initially begins as a mish-mash of all
 * three kinds of types. The type-checker's inference algorithm then infers
 * and constrains as many types as possible into concrete or composite types.
 * Finally the run-time uses that existing type information in tandem with
 * run-time type information to perform as-optimized-as-possible evaluation.
 */
public abstract class Type {
}
