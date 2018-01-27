package tacit.sbt

import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._
import sbt.Keys._
import sbt._

object RootTasks {
  lazy val jvm = inputKey[Unit]("run JVM code")

  lazy val js = taskKey[Unit]("compile JS dev code")
}
