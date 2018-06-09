package tacit.sbt

import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._
import org.scalajs.sbtplugin._
import org.scalajs.sbtplugin.cross._
import sbt._

import BenchConfig._
import CustomSettings._
import Dependencies._

object ProjectExtensions {
  implicit final class ProjectHelper(
    val project: Project
  ) extends AnyVal {
    def withCustomSettings() =
      project.settings(allCustomSettings)

    def withBenchConfig() =
      project.configs(Bench)

    def libraryDependencies(
      dependencies: Def.Initialize[ModuleID]*
    ) =
      project.settings(
        toSettings(dependencies)
      )

    def projectDependencies(
      dependencies: ClasspathDep[ProjectReference]*
    ) =
      project.dependsOn(dependencies: _*)

    def rootJVM(otherProject: Project) =
      project.settings(
        RootTasks.jvm in Compile :=
          (Keys.run in Compile in otherProject).evaluated
      )

    def rootJS(otherProject: Project) =
      project.settings(
        RootTasks.js in Compile :=
          (fastOptJS in Compile in otherProject).value
      )

    def withJSMain() =
      project
        .enablePlugins(ScalaJSPlugin)
        .settings(
          scalaJSUseMainModuleInitializer := true
        )
  }

  implicit final class CrossProjectHelper(
    val crossProject: CrossProject
  ) extends AnyVal {
    def withCustomSettings() =
      crossProject.settings(allCustomSettings)

    def libraryDependencies(
      dependencies: Def.Initialize[ModuleID]*) =
      crossProject.settings(
        toSettings(dependencies)
      )
  }
}
