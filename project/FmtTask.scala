package tacit.sbt

import org.scalafmt.sbt.ScalafmtPlugin.autoImport._
import sbt._

import BenchConfig._

object FmtTask {
  lazy val fmt = taskKey[Unit]("format all code")

  lazy val fmtTaskSettings = Seq(fmt := {
    (scalafmt in Compile).value
    (scalafmt in Test).value
    (scalafmt in Bench).value
    (scalafmtSbt in Compile).value
  })
}
