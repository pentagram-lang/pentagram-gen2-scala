import Dependencies._
import ProjectExtensions._

lazy val core = (project in file("core"))
  .withCustomSettings
  .libraryDependencies(
    fastparse,
    scalatest,
    scalameter,
    nameof
  )

lazy val consoleShell = (project in file("console-shell"))
  .withCustomSettings
  .libraryDependencies(
    jline
  )
  .projectDependencies(
    core
  )

lazy val root = (project in file("."))
  .aggregate(core, consoleShell)
  .runOther(consoleShell)
