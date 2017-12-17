import sbt._
import Keys._

object BenchConfig {
  lazy val Bench = config("bench") extend(Test)
  lazy val bench = taskKey[Unit]("run benchmark")

  lazy val benchSettings = (
    inConfig(Bench)(Defaults.testSettings)
    ++ (ivyConfigurations += Bench extend(Provided))
    ++ (bench in Bench := (test in Bench).value)
    ++ (unmanagedSourceDirectories in Bench :=
        (unmanagedSourceDirectories in Test).value)
  )
}
