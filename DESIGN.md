# Variables

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
//   constraints: exists function +(C, String) -> D
//
//   deductions: return type is D
//
func foo(bar, baz) {
  return bar + baz + "bam"
}
```
