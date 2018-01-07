package tacit.sbt

import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._
import org.scalajs.sbtplugin._
import org.scalajs.sbtplugin.cross._
import sbt._

import BenchConfig._
import FmtTask._
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

    def withFmtTask() =
      project.settings(fmtSettings)

    def libraryDependencies(
      dependencies: Def.Initialize[ModuleID]*
    ) =
      project.settings(
        mapLibraryDependencies(dependencies)
      )

    def projectDependencies(
      dependencies: ClasspathDep[ProjectReference]*
    ) =
      project.dependsOn(dependencies: _*)

    def runOther(otherProject: Project) =
      project.settings(
        Keys.run in Compile :=
          (Keys.run in Compile in otherProject).evaluated
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
        mapLibraryDependencies(dependencies)
      )
  }
}
