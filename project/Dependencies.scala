import sbt._
import Keys._
import BenchConfig._

object Dependencies {
  lazy val fastparse = "com.lihaoyi" %% "fastparse" % "0.4.3"
  lazy val jline = "org.jline" % "jline-reader" % "3.3.1"
  lazy val scalatest = "org.scalatest" %% "scalatest" % "3.0.3" % Test
  lazy val scalameter = "com.storm-enroute" %% "scalameter" % "0.8.2" % Bench
  lazy val nameof = "com.github.dwickern" %% "scala-nameof" % "1.0.3" % Provided

  lazy val customResolvers =
    resolvers ++= Seq(
      Resolver.sonatypeRepo("releases")
    )

  lazy val customTestFrameworks = Seq(
    new TestFramework("org.scalameter.ScalaMeterFramework")
  )
}
