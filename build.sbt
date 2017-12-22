import tacit.sbt.CustomCrossProject._
import tacit.sbt.Dependencies._
import tacit.sbt.ProjectExtensions._

lazy val core = (crossProject.crossType(CustomCrossType) in file("core"))
  .withCustomSettings
  .libraryDependencies(
    fastparse,
    scalatest,
    scalameter,
    nameof
  )
lazy val coreJVM = core.jvm.withBenchConfig
lazy val coreJS = core.js

lazy val consoleShell = (project in file("console-shell"))
  .withCustomSettings
  .libraryDependencies(
    jline
  )
  .projectDependencies(
    coreJVM
  )

lazy val webShell = (project in file("web-shell"))
  .withCustomSettings
  .withJSMain
  .libraryDependencies(
    dom,
    scalatags,
    scalacss,
    scalacssExt
  )
  .projectDependencies(
    coreJS
  )

lazy val root = (project in file("."))
  .aggregate(
    coreJVM,
    coreJS,
    consoleShell,
    webShell
  )
  .runOther(consoleShell)

cancelable in Global := true
