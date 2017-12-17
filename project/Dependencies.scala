import sbt._
import org.scalajs.sbtplugin.cross._
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._
import sbt.Keys._
import sbt.Def._
import BenchConfig._

object Dependencies {
  lazy val customScalaVersion = scalaVersion := "2.12.4"

  lazy val fastparse =
    setting("com.lihaoyi" %%% "fastparse" % "1.0.0")
  lazy val jline =
    setting("org.jline" % "jline-reader" % "3.5.1")
  lazy val dom =
    setting("org.scala-js" %%% "scalajs-dom" % "0.9.4")
  lazy val scalatest =
    setting("org.scalatest" %%% "scalatest" % "3.0.4" % Test)
  lazy val scalameter =
    setting("com.storm-enroute" %% "scalameter" % "0.8.2" % Bench)
  lazy val nameof =
    setting("com.github.dwickern" %% "scala-nameof" % "1.0.3" % Provided)

  lazy val customResolvers =
    resolvers ++= Seq(
      Resolver.sonatypeRepo("releases")
    )

  lazy val customTestFrameworks =
    testFrameworks += new TestFramework("org.scalameter.ScalaMeterFramework")

  def mapLibraryDependencies(dependencies: Seq[Def.Initialize[ModuleID]]) =
    dependencies.map(dependency =>
      libraryDependencies += dependency.value)
}
