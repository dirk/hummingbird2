# Design

## Variables

Mutable and immutable slots are explicitly separated in Hummingbird:

- `let` defines immutable bindings.
- `var` defines mutable ones.

```swift
let foo = "foo" // Type is inferred to be Utf8String.
foo = "bar" // This is an error!

var baz: Utf8String = "baz"
baz = "bam" // This is okay.
baz = 123 // This is an error!
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
//   deductions: type of foo = Closure<(A, B), C>
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
//   deductions: type of foo = Closure<(Utf8String), Utf8String>
//
let foo = |bar: Utf8String| -> Utf8String {
  if bar == "bar" {
    return "baz"
  } else {
    return bar
  }
}
```

## Control Flow

### If

If, else-if, and else all follow the traditional design:

```swift
let truthy = true

if truthy {
  print("Truthy")
} else if !truthy {
  print("Not truthy")
} else {
  print("Actually unreachable")
}
```

### While

The traditional `while` loop is also probably as you'd expect:

```swift
var i = 0

while i < 10 {
  i += 1

  if i % 2 == 0 {
    continue // Skip even items
  } else if i == 7 {
    break // Stop short
  } else {
    print(i)
  }
}
```

### For

There are two formulations of the `for` loop structure. The convenient `for-in` statement:

```swift
let numbers = [1, 2, 3]

// numbers conforms to the IntoIterator trait so it can be used in a
// for-in statement.
for i in numbers {
  print(i)
}
```

And the more traditional three-part `for` statement:

```swift
for var i = 1; i <= 3; i++ {
  print(i)
}
```

## Classes

Classes are strongly inspired by Swift classes, but with a deliberately more simplistic initializer and inheritance model. They also draw from ECMAScript 6 classes and Rust structs.

```swift
// Generics are fully supported.
class Stack<Element> {
  var items: Array<Element>
  var depth = 0

  // Using a unary enum Capacity to target a specific constructor via
  // ad hoc polymorphism (overloading).
  init(initialCapacity: Capacity) {
    // Three things to note here:
    //   - The type-checker will infer the Element generic parameter for the
    //     array constructor.
    //   - The implicit self variable; this is the only implicit variable in
    //     constructors, instance methods, and class methods.
    self.items = new Array(initialCapacity)
  }

  // The companion to init is finalize. Classes may define multiple
  // initializers, but only zero or one finalizers.

  // Type-checker infers the type of item to be Element.
  func push(item) {
    this.items.push(item)
    this.depth += 1
  }
}
```

### Property Initialization

Hummingbird requires that all properties be defined by the end of the initializer, but does not enforce that requirement within the initializer. This means that you can call instance methods before all properties are set (in contrast to Swift, where this is a compilation error).

The rationale for this decision is that there is a balance between freedom and safety. Swift opts for more safety in this regard, whereas Hummingbird chooses to give programmers freedom (at the risk of underfined behavior).

## Enums

Inspired largely by ML and Rust. Enums are a value type that can store associated values within their variants.

```swift
// Using ad-hoc polymorphism.
enum List<Element> {
  case Nil,
  case Cons(Element, List<Element>),
}
```

There is also a unary enum syntax:

```swift
// Standard collection capacity type.
enum Capacity(Int64)
```

## Tuples

Are immutable, constant-sized, and can contain arbitrary types.

```swift
// Type-checker will infer the type of pair to be (Int64, Utf8String)
let pair = (1, "one")

// Destructuring assignment is supported in conjunction with type inference.
let (anInt, aString) = pair
```
