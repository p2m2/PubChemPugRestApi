name := "PubChemPugRestApi"
version := "0.1"
scalaVersion := "2.13.7"
organization := "com.github.p2m2"
organizationName := "p2m2"
organizationHomepage := Some(url("https://www6.inrae.fr/p2m2"))
licenses := Seq("MIT License" -> url("http://www.opensource.org/licenses/mit-license.php"))
homepage := Some(url("https://github.com/p2m2/PubChemPugRestApi"))
description := "get compound information from PubChem API : https://pubchem.ncbi.nlm.nih.gov/rest/pug"

scmInfo := Some(
  ScmInfo(
    url("https://github.com/p2m2/PubChemPugRestApi"),
    "scm:git@github.com:p2m2/PubChemPugRestApi.git"
  )
)

developers := List(
  Developer("ofilangi", "Olivier Filangi", "olivier.filangi@inrae.fr",url("https://github.com/ofilangi"))
)

credentials += {
  val realm = scala.util.Properties.envOrElse("REALM_CREDENTIAL", "" )
  val host = scala.util.Properties.envOrElse("HOST_CREDENTIAL", "" )
  val login = scala.util.Properties.envOrElse("LOGIN_CREDENTIAL", "" )
  val pass = scala.util.Properties.envOrElse("PASSWORD_CREDENTIAL", "" )

  val file_credential = Path.userHome / ".sbt" / ".credentials"

  if (reflect.io.File(file_credential).exists) {
    Credentials(file_credential)
  } else {
    Credentials(realm,host,login,pass)
  }
}

publishTo := {
  if (isSnapshot.value)
    Some("Sonatype Snapshots Nexus" at "https://oss.sonatype.org/content/repositories/snapshots")
  else
    Some("Sonatype Snapshots Nexus" at "https://oss.sonatype.org/service/local/staging/deploy/maven2")
}
publishConfiguration := publishConfiguration.value.withOverwrite(true)
publishLocalConfiguration := publishLocalConfiguration.value.withOverwrite(true)
pomIncludeRepository := { _ => false }
publishMavenStyle := true


libraryDependencies ++= Seq(
  "com.lihaoyi" %% "utest" % "0.8.1" % "test",
  "org.scala-lang.modules" %% "scala-xml" % "2.1.0",
  "com.lihaoyi" %% "upickle" % "2.0.0"
)

testFrameworks += new TestFramework("utest.runner.Framework")