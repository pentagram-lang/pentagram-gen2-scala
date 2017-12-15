import sbt._
import BenchConfig._
import CustomSettings._
import Dependencies._

object ProjectExtensions {
  implicit final class ProjectHelper(val project: Project) extends AnyVal {
    def withCustomSettings =
      project
        .settings(customScalaVersion)
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

    def projectDependencies(dependencies: ClasspathDep[ProjectReference]*) =
      project.dependsOn(dependencies: _*)

    def runOther(otherProject: Project) =
      project.settings(
        Keys.run in Compile :=
          (Keys.run in Compile in otherProject).evaluated
      )
  }
}
