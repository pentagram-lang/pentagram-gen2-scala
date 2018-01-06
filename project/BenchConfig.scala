package tacit.sbt

import sbt._
import sbt.Keys._
import org.scalafmt.sbt.ScalafmtPlugin

object BenchConfig {
  lazy val Bench = config("bench") extend (Test)
  lazy val bench = taskKey[Unit]("run benchmark")

  lazy val benchSettings = (
    inConfig(Bench)(Defaults.testSettings)
      ++ inConfig(Bench)(
        ScalafmtPlugin.scalafmtConfigSettings)
      ++ (ivyConfigurations += Bench extend (Provided))
      ++ (bench in Bench := (test in Bench).value)
      ++ (unmanagedSourceDirectories in Bench :=
        (unmanagedSourceDirectories in Test).value)
  )
}
