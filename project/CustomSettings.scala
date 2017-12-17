package tacit.sbt

import sbt._
import sbt.Keys._
import tacit.sbt.BenchConfig._
import tacit.sbt.Dependencies._

object CustomSettings {
  lazy val customScalacOptions =
    scalacOptions ++= Seq(
      "-deprecation",
      "-encoding", "UTF-8",
      "-feature",
      "-unchecked",
      "-Xlint",
      "-Ywarn-dead-code",
      "-Ywarn-numeric-widen",
      "-Ywarn-unused-import"
    )

  lazy val testFilePattern = "*.Test.scala"
  lazy val benchFilePattern = "*.Bench.scala"

  lazy val customSourceRules = Seq(
    scalaSource in Compile := baseDirectory.value,
    scalaSource in Test := baseDirectory.value,
    scalaSource in Bench := baseDirectory.value,

    excludeFilter in Compile := testFilePattern || benchFilePattern,
    excludeFilter in Test := NothingFilter,
    includeFilter in Test := testFilePattern,
    excludeFilter in Bench := NothingFilter,
    includeFilter in Bench := benchFilePattern
  )

  lazy val customTestOptions = Seq(
    logBuffered in Test := false,
    customTestFrameworks,
    parallelExecution in Bench := false
  )

  lazy val allCustomSettings = (
    customScalaVersion
    ++ benchSettings
    ++ customSourceRules
    ++ customTestOptions
    ++ customScalacOptions
    ++ customResolvers
  )
}
