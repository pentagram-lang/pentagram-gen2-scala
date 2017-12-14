scalaVersion := "2.12.3"

lazy val Bench = config("bench") extend(Test)
lazy val bench = taskKey[Unit]("run benchmark")

lazy val testFilePattern = "*.Test.scala"
lazy val benchFilePattern = "*.Bench.scala"

lazy val root = (project in file(".")).configs(Bench).settings(
  inConfig(Bench)(Defaults.testSettings),
  ivyConfigurations += Bench extend(Provided),
  bench in Bench := (test in Bench).value,

  resolvers += Resolver.sonatypeRepo("releases"),
  libraryDependencies ++= Seq(
    "com.lihaoyi" %% "fastparse" % "0.4.3",
    "org.jline" % "jline-reader" % "3.3.1",
    "org.scalatest" %% "scalatest" % "3.0.3" % Test,
    "com.storm-enroute" %% "scalameter" % "0.8.2" % Bench,
    "com.github.dwickern" %% "scala-nameof" % "1.0.3" % Provided
  ),

  scalacOptions ++= Seq(
    "-deprecation",
    "-encoding", "UTF-8",
    "-feature",
    "-unchecked",
    "-Xlint",
    "-Ywarn-dead-code",
    "-Ywarn-numeric-widen",
    "-Ywarn-unused-import"
  ),

  scalaSource in Compile := baseDirectory.value / "src",
  scalaSource in Test := baseDirectory.value / "src",
  scalaSource in Bench := baseDirectory.value / "src",

  excludeFilter in Compile := testFilePattern || benchFilePattern,
  excludeFilter in Test := NothingFilter,
  includeFilter in Test := testFilePattern,
  excludeFilter in Bench := NothingFilter,
  includeFilter in Bench := benchFilePattern,

  logBuffered in Test := false,
  testFrameworks += new TestFramework("org.scalameter.ScalaMeterFramework"),
  parallelExecution in Bench := false)
