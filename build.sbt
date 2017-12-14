import BenchConfig._
import Dependencies._
import CustomSettings._

scalaVersion := "2.12.3"

lazy val root = (project in file("."))
  .configs(Bench)
  .settings(benchSettings : _*)
  .settings(customSourceRules)
  .settings(customTestOptions)
  .settings(customScalacOptions)
  .settings(
    customResolvers,
    libraryDependencies ++= Seq(
      fastparse,
      jline,
      scalatest,
      scalameter,
      nameof
    )
  )
