# Hummingbird 2

This is an iteration of the [Hummingbird 1 project][]. It seeks to build on core concepts learned there and add new ideas from subsequent developments in the [PL][] communities.

[Hummingbird 1 project]: https://github.com/dirk/hummingbird
[PL]: https://en.wikipedia.org/wiki/Programming_language

#### Visions & goals

- **Gradual typing system**: as laid out in the [design document][], type specifications should be mostly optional. The capabilities of specializing JIT compilers mean types are now an option, not a requirement for PL implementation. Therefore the type system should be designed firstly for programmer usability and encouraging safety; with the actual language implementation taking a supporting role.
- **Easy hot reloading**: the design and implementation should make it easy for the user to hot-reload portions of their code. The type system and interpreter should be crafted with this in mind.
- **Accessible tooling**: the Language Server protocol is an excellent starting point. The complexities of specializing JIT compilers enforces a sizable start-up penalty on tools. Therefore the ecosystem should be built with this in mind and seek to avoid time penalties (through strategies like compilers-as-language-servers and ahead-of-time compilation).

[design document]: DESIGN.md
