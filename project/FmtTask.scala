package tacit.sbt

import org.scalafmt.sbt.ScalafmtPlugin.autoImport._
import sbt._

import BenchConfig._

object FmtTask {
  lazy val fmt = taskKey[Unit]("format all code")

  lazy val fmtCheck = taskKey[Unit]("check all code formatting")

  lazy val fmtTaskSettings = Seq(fmt := {
    (scalafmt in Compile).value
    (scalafmt in Test).value
    (scalafmt in Bench).value
    (scalafmtSbt in Compile).value
  })

  lazy val fmtCheckTaskSettings = Seq(fmtCheck := {
    (scalafmtCheck in Compile).value
    (scalafmtCheck in Test).value
    (scalafmtCheck in Bench).value
    (scalafmtSbtCheck in Compile).value
  })
}
