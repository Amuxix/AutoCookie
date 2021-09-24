name := "Auto Cookie"

version := "6"

scalaVersion := "3.0.2"

enablePlugins(ScalaJSPlugin)

scalaJSUseMainModuleInitializer := true

libraryDependencies ++= Seq(
  "org.scala-js" %%% "scalajs-dom" % "1.1.0",
  "com.raquo" %%% "laminar" % "0.13.0",
).map(_.cross(CrossVersion.for3Use2_13))
