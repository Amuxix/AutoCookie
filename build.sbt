name := "Auto Cookie"

version := "6"

scalaVersion := "3.0.2"

enablePlugins(ScalaJSPlugin)
enablePlugins(ScalablyTypedConverterPlugin)

scalaJSUseMainModuleInitializer := true

libraryDependencies += ("org.scala-js" %%% "scalajs-dom" % "1.1.0").cross(CrossVersion.for3Use2_13)

Compile / npmDependencies ++= Seq("@types/cookieclicker" -> "2.31.5")
