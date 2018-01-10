package tacit.sbt

import sbt.Keys._
import sbt._
import wartremover.WartRemover.autoImport._

import BenchConfig._
import CompileAllTask._
import Dependencies._
import FmtTask._

object CustomSettings {
  lazy val customScalacOptions =
    scalacOptions ++= Seq(
      "-deprecation",
      "-encoding",
      "UTF-8",
      "-feature",
      "-target:jvm-1.8",
      "-unchecked",
      "-Xfatal-warnings",
      "-Xfuture",
      "-Xlint",
      "-Xsource:2.12",
      "-Ywarn-adapted-args",
      "-Ywarn-dead-code",
      "-Ywarn-extra-implicit",
      "-Ywarn-inaccessible",
      "-Ywarn-nullary-override",
      "-Ywarn-nullary-unit",
      "-Ywarn-numeric-widen",
      "-Ywarn-unused:_",
      "-Ywarn-unused-import",
      "-Ywarn-value-discard"
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

  lazy val customWartremoverErrors = Seq(
    wartremoverErrors := Warts
      .allBut(Wart.Equals, Wart.Nothing, Wart.PublicInference)
  )

  lazy val allCustomSettings = (
    customScalaVersion
      ++ benchSettings
      ++ fmtSettings
      ++ compileAllSettings
      ++ customSourceRules
      ++ customTestOptions
      ++ customScalacOptions
      ++ customResolvers
      ++ customWartremoverErrors
  )

  lazy val allRootCustomSettings = (
    benchSettings
      ++ fmtSettings
      ++ compileAllSettings
  )
}
