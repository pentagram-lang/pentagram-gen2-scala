import sbt._
import org.scalajs.sbtplugin.cross._
import BenchConfig._
import CustomSettings._
import Dependencies._

object ProjectExtensions {
  implicit final class ProjectHelper(val project: Project) extends AnyVal {
    def withCustomSettings =
      project
        .settings(customScalaVersion)
        .settings(benchSettings : _*)
        .settings(customSourceRules)
        .settings(customTestOptions)
        .settings(customScalacOptions)
        .settings(customResolvers)

    def withBenchConfig =
      project
        .configs(Bench)

    def libraryDependencies(dependencies: Def.Initialize[ModuleID]*) =
      project.settings(
        dependencies.map(dependency =>
          Keys.libraryDependencies += dependency.value))

    def projectDependencies(dependencies: ClasspathDep[ProjectReference]*) =
      project.dependsOn(dependencies: _*)

    def runOther(otherProject: Project) =
      project.settings(
        Keys.run in Compile :=
          (Keys.run in Compile in otherProject).evaluated
      )
  }

  implicit final class CrossProjectHelper(val crossProject: CrossProject) extends AnyVal {
    def withCustomSettings =
      crossProject
        .settings(customScalaVersion)
        .settings(benchSettings : _*)
        .settings(customSourceRules)
        .settings(customTestOptions)
        .settings(customScalacOptions)
        .settings(customResolvers)

    def libraryDependencies(dependencies: Def.Initialize[ModuleID]*) =
      crossProject.settings(
        dependencies.map(dependency =>
          Keys.libraryDependencies += dependency.value))
  }
}
