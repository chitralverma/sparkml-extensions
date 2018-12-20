organization := "com.mav.now"
name := "sparkml-extensions"

scalaVersion := "2.11.12"
crossScalaVersions := Seq("2.11.12", "2.12.7")

sparkVersion := "2.4.0"
spAppendScalaVersion := true
sparkComponents ++= Seq("sql", "mllib")

updateOptions := updateOptions.value.withCachedResolution(true)
scalastyleFailOnError in ThisBuild := true
scalafmtConfig in ThisBuild := file(".scalafmt.conf")
scalafmtOnCompile in Compile := true
scalafmtTestOnCompile in Compile := true

libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.5"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % "test"

licenses in ThisBuild += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0"))

artifactName := { (sv: ScalaVersion, module: ModuleID, artifact: Artifact) =>
  s"${artifact.name}_${sv.binary}-${sparkVersion.value}_${module.revision}.${artifact.extension}"
}
