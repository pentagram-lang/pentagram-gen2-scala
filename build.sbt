import Dependencies._
import ProjectExtensions._

scalaVersion := "2.12.3"

lazy val root = (project in file("."))
  .withCustomSettings
  .libraryDependencies(
    fastparse,
    jline,
    scalatest,
    scalameter,
    nameof
  )
