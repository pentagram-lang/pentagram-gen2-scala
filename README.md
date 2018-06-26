# Tacit <a href="https://github.com/tacit-lang/tacit" target="_blank"><img width="30" height="30" src="jsShell/art/favicon.svg?raw=true"/></a>

## About

Tacit is a friendly programming language that uses **ergonomics**, **determinism**, and **efficiency** to help you express your creativity.

- With powerful features like **significant whitespace** and **functional programming**, the Tacit language works together with you and helps you out at every step.

- The Tacit community is here to help too! We know how tough coding can be, and we do our best to make this a safe space where **kindness** and **empathy** are the norm.

### Status

<a href="https://app.shippable.com/github/tacit-lang/tacit/dashboard" target="_blank"><img src="https://img.shields.io/shippable/5b1c522acd42f90700239776.svg?style=flat-square"/></a>
<a href="https://www.codacy.com/app/tacit-lang/tacit" target="_blank"><img src="https://img.shields.io/codacy/grade/9c51ed7b9a0b4c49bdfdd073a0d2b1a3.svg?style=flat-square"/></a>

_Almost all features are not yet implemented!_

Feature statuses and priorities are all tracked at the [Tacit Project Overview](https://trello.com/b/pW0C3rs1/tacit-project-overview).

## Developing

### Prerequisites

You'll need to install a JDK (to run the Scala compiler) and [SBT](http://www.scala-sbt.org/) (to download the Scala compiler and library dependencies).

You'll also need to install [Git Large File Storage](https://git-lfs.github.com/) (to download the fonts used by the JS web shell).

Optionally, for a smoother web shell development workflow, you can install [Browsersync](https://browsersync.io/).

### Workflow

Here are some important `sbt` commands:

- `jvm` (to run the JVM console shell)
- `js` (to compile the JS web shell)
- `test` (to run all tests)
- `~testQuick` (to run only affected tests, and run them again when any source file changes)
- `bench` (to run benchmarks)
- `fmt` (to reformat all source files)
- `compileAll` (to compile all source files including tests)

When working on the web shell, you can use the included Browsersync + `sbt` wrapper script:

- `./browser-sync-sbt.sh` (to automatically reload the web console after the SBT build finishes)

### Code style

For Scala code style, see the [Tacit wiki page](../../wiki/Scala-code-style).

## Deploying

To download dependencies, compile, test, check formatting, and build JavaScript:

```
sbt update
sbt compileAll
sbt test
sbt fmtCheck
sbt jsShell/fullOptJS
```

To deploy to GitHub pages, you can try using an automated script like this Gist:

> https://gist.github.com/dcecile/b03ea8c5a807ca1ff8068908ce669d54

## License

This project is released under the MIT License (see [LICENSE.md](LICENSE.md) for details).
