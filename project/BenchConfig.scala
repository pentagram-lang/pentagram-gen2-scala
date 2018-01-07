package tacit.sbt

import org.scalafmt.sbt.ScalafmtPlugin._
import sbt.Keys._
import sbt._

object BenchConfig {
  lazy val Bench = config("bench") extend (Test)
  lazy val bench = taskKey[Unit]("run benchmark")

  lazy val benchSettings = (
    inConfig(Bench)(Defaults.testSettings)
      ++ inConfig(Bench)(scalafmtConfigSettings)
      ++ (ivyConfigurations += Bench extend (Provided))
      ++ (bench in Bench := (test in Bench).value)
      ++ (unmanagedSourceDirectories in Bench :=
        (unmanagedSourceDirectories in Test).value)
  )
}
