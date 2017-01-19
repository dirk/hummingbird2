# Design

## Variables

Mutable and immutable slots are explicitly separated in Hummingbird:

- `let` defines immutable bindings.
- `var` defines mutable ones.

```

```

## Callables

There are two types of callables in Hummingbird: static functions and dynamic closures (heap-allocated).

### Functions

Are defined using the `func` keyword. They support parametric polymorphism (via specialization) and ad hoc polymorphism (aka. overloading). The compiler will try to pick the most precise definition. The specifics of these precision rules are to-be-determined.

```swift
// Specializations will be created at compile time.
//
// Type-checker will infer the following ("given"), check the constraints,
// and make deductions:
//
//   given: A = type of bar,
//          B = type of baz,
//          exists type C such that +(A, B) -> C,
//
//   constraints: exists function +(C, Utf8String) -> D
//
//   deductions: return type is D
//
func foo(bar, baz) {
  return bar + baz + "bam"
}
```

A definition using concrete types and overloading would be:

```swift
// Type-checker will check the following constraints (which will pass as
// they are defined in the standard runtime):
//
//   constraints: exists function +(Utf8String, Utf8String) -> Utf8String
//
func foo(bar: Utf8String, baz: Utf8String) -> Utf8String {
  return bar + baz + "bam"
}

// Type-checker will make an inference and check one constraint:
//
//   given: Int32#toString() -> Utf8String
//
//   constraints: exists function +(Utf8String, Utf8String) -> Utf8String
//
func foo(bar: Int32) -> Utf8String {
  return bar.toString() + " bams"
}
```

### Closures

Closures are allocated on the heap and capture ("close") relevant values from their scope. A closure is therefore a value that can be passed around which holds the following details:

- Pointer to the location of the executable code.
- Map of captured values to be used by the executable code.

Closures use a different syntax than functions. It has been [adopted from Rust][] with the intent that it communicates the fact that closures are really a kind of literal.

[adopted from Rust]: https://doc.rust-lang.org/stable/book/closures.html#syntax

```rust
// Type-checker will infer, constrain, and deduce the following:
//
//   given: A = type of bar,
//          B = type of baz
//
//   constraints: exists function +(A, B) -> C
//
//   deductions: type of foo = Closure((A, B), C)
//
let foo = |bar, baz| bar + baz
```

The compiler will implicitly return the result of a single expression in a closure's body. For more complex statements one must use a block and an explicit `return`.

```rust
// Type-checker will constrain the following:
//
//   given: A = type of foo
//
//   constraints: exists function ==(Utf8String, Utf8String) -> Bool
//
//   deductions: type of foo = Closure((Utf8String), Utf8String)
//
let foo = |bar: Utf8String| -> Utf8String {
  if bar == "bar" {
    return "baz"
  } else {
    return bar
  }
}
```
