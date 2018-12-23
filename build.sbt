organization := "com.github.chitralverma"

name := "sparkml-extensions"

sparkVersion := "2.4.0"
sparkComponents ++= Seq("core", "streaming", "sql", "catalyst", "mllib")

spName := "chitralverma/sparkml-extensions"
spIncludeMaven := true
spAppendScalaVersion := true

useGpg := true
updateOptions := updateOptions.value.withCachedResolution(true)

fork := true
parallelExecution in Test := false

scalaVersion := {
  if (sparkVersion.value >= "2.0.0") {
    "2.11.11"
  } else {
    "2.10.6"
  }
}

crossScalaVersions := {
  if (sparkVersion.value >= "2.3.0") {
    Seq("2.11.11")
  } else {
    Seq("2.10.6", "2.11.11")
  }
}

javacOptions ++= {
  if (sparkVersion.value >= "2.1.1") {
    Seq("-source", "1.8", "-target", "1.8")
  } else {
    Seq("-source", "1.7", "-target", "1.7")
  }
}

javaOptions ++= Seq("-Xms2G", "-Xmx2G", "-XX:MaxPermSize=2048M", "-XX:+CMSClassUnloadingEnabled")
scalacOptions ++= Seq("-deprecation", "-unchecked")

publishMavenStyle := true
pomIncludeRepository := { x =>
  false
}

coverageHighlighting := {
  if (sparkVersion.value >= "2.0.0") true else false
}

coverageHighlighting := {
  if (scalaBinaryVersion.value == "2.10") false else true
}

scalastyleFailOnError in ThisBuild := true
scalafmtConfig in ThisBuild := file(".scalafmt.conf")
scalafmtOnCompile in Compile := true
scalafmtTestOnCompile in Compile := true

libraryDependencies += "com.holdenkarau" %% "spark-testing-base" % "2.3.1_0.10.0" % "test"

credentials ++= Seq(
  Credentials(Path.userHome / ".sbt" / ".sonatype_credential"),
  Credentials(Path.userHome / ".sbt" / ".sparkcredentials")
)

artifactName := { (sv: ScalaVersion, module: ModuleID, artifact: Artifact) =>
  s"${artifact.name}_${sv.binary}-${sparkVersion.value}_${module.revision}.${artifact.extension}"
}

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

licenses in ThisBuild += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0"))
homepage := Some(url("https://github.com/chitralverma/sparkml-extensions"))

pomExtra :=
  <scm>
            <url>git@github.com:chitralverma/sparkml-extensions.git</url>
            <connection>scm:git@github.com:chitralverma/sparkml-extensions.git</connection>
        </scm>
                <developers>
                    <developer>
                        <id>chitralverma</id>
                        <name>Chitral Verma</name>
                        <url>https://github.com/chitralverma/</url>
                        <email>chitralverma@gmail.com</email>
                    </developer>
                </developers>
