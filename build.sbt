import Dependencies._
import ProjectExtensions._

lazy val core = (project in file("core"))
  .withCustomSettings
  .libraryDependencies(
    fastparse,
    jline,
    scalatest,
    scalameter,
    nameof
  )

lazy val root = (project in file("."))
  .aggregate(core)
  .runOther(core)
