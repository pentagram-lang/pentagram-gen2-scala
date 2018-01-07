package tacit.sbt

import sbt._
import org.scalafmt.sbt.ScalafmtPlugin.autoImport._

import BenchConfig._

object FmtTask {
  lazy val fmt = taskKey[Unit]("format all code")

  lazy val fmtSettings = Seq(fmt := {
    (scalafmt in Compile).value
    (scalafmt in Test).value
    (scalafmt in Bench).value
    (scalafmtSbt in Compile).value
  })
}
