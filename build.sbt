import tacit.sbt.CustomCrossProject._
import tacit.sbt.Dependencies._
import tacit.sbt.ProjectExtensions._

lazy val core =
  (crossProject.crossType(CustomCrossType) in file("core"))
    .withCustomSettings()
    .libraryDependencies(
      fastparse,
      scalatest,
      scalameter,
      nameof
    )
lazy val coreJVM =
  core.jvm
    .withBenchConfig()
lazy val coreJS =
  core.js

lazy val jvmShell =
  (project in file("jvmShell"))
    .withCustomSettings()
    .libraryDependencies(
      jline
    )
    .projectDependencies(
      coreJVM
    )

lazy val jsShell =
  (project in file("jsShell"))
    .withCustomSettings()
    .withJSMain()
    .libraryDependencies(
      dom,
      scalatags,
      scalacss,
      scalacssExt
    )
    .projectDependencies(
      coreJS
    )

lazy val root =
  (project in file("."))
    .aggregate(
      coreJVM,
      coreJS,
      jvmShell,
      jsShell
    )
    .rootJVM(jvmShell)
    .rootJS(jsShell)

cancelable in Global := true
