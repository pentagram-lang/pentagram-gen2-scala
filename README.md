# Tacit
_A programming language_

[![Codacy grade](https://img.shields.io/codacy/grade/9c51ed7b9a0b4c49bdfdd073a0d2b1a3.svg?style=flat-square)](https://www.codacy.com/app/tacit-lang/tacit)

## Developing

### Prerequisites

You'll need to install a JDK (to run the Scala compiler) and [SBT](http://www.scala-sbt.org/) (to download the Scala compiler and library dependencies).

Optionally, for a smoother web shell development workflow, you can install [Browsersync](https://browsersync.io/).

### Workflow

Here are some important `sbt` commands:

- `run` (to run the console shell)
- `fastOptJs` (to compile the web shell to JS)
- `test` (to run all tests)
- `~testQuick` (to run only affected tests, and run them again when any source file changes)
- `bench` (to run benchmarks)
- `fmt` (to reformat all source files)
- `compileAll` (to compile all source files including tests)

When working on the web shell, you can use the included Browsersync + `sbt` wrapper script:

- `./browser-sync-sbt.sh` (to automatically reload the web console after the SBT build finishes)

### Code style

For Scala code style, see the [Tacit wiki page](../../wiki/Scala-code-style).

## License

This project is released under the MIT License (see [LICENSE.md](LICENSE.md) for details).
