package tacit.sbt

import sbt.Keys._
import sbt._

import BenchConfig._

object CompileAllTask {
  lazy val compileAll = taskKey[Unit]("compile all code")

  lazy val compileAllTaskSettings = Seq(compileAll := {
    (compile in Compile).value
    (compile in Test).value
    (compile in Bench).value
  })
}
