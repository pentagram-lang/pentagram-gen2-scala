import sbt._
import Keys._
import BenchConfig._
import Dependencies._

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
    testFrameworks ++= customTestFrameworks,
    parallelExecution in Bench := false
  )
}
