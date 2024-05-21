

ThisBuild / scalaVersion     := "2.13.14"
ThisBuild / version          := "0.1.0-SNAPSHOT"

lazy val root = (project in file("."))
  .settings(
    name := "simple-tagless-final-with-mtl",
    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats-core" % "2.1.0",
      "org.typelevel" %% "cats-free" % "2.6.1",
      "org.typelevel" %% "cats-effect" % "3.1.1",
      "org.typelevel" %% "cats-mtl" % "1.3.0",
      "org.scalameta" %% "munit" % "0.7.29",
    )
  )
