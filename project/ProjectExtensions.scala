import sbt._
import BenchConfig._
import CustomSettings._
import Dependencies._

object ProjectExtensions {
  implicit final class ProjectHelper(val project: Project) extends AnyVal {
    def withCustomSettings =
      project
        .configs(Bench)
        .settings(benchSettings : _*)
        .settings(customSourceRules)
        .settings(customTestOptions)
        .settings(customScalacOptions)
        .settings(customResolvers)

    def libraryDependencies(dependencies: ModuleID*) =
      project.settings(
        Keys.libraryDependencies ++= dependencies
      )
  }
}
