scalaVersion := "2.12.1"

logBuffered in Test := false

libraryDependencies ++= Seq(
  "com.lihaoyi" %% "fastparse" % "0.4.3",
  "org.jline" % "jline-reader" % "3.3.1",
  "org.scalatest" %% "scalatest" % "3.0.3" % Test,
  "com.github.dwickern" %% "scala-nameof" % "1.0.3" % Provided)

scalaSource in Compile := baseDirectory.value / "src"
scalaSource in Test := baseDirectory.value / "src"

val testFilePattern = "*.Test.scala"
excludeFilter in Compile := testFilePattern
excludeFilter in Test := NothingFilter
includeFilter in Test := testFilePattern
