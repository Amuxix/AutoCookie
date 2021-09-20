name := "Auto Cookie"

version := "6"

scalaVersion := "3.0.2"

enablePlugins(ScalaJSPlugin)

scalaJSUseMainModuleInitializer := true

libraryDependencies += ("org.scala-js" %%% "scalajs-dom" % "1.1.0").cross(CrossVersion.for3Use2_13)

scalaJSLinkerConfig ~= { _.withModuleKind(ModuleKind.NoModule) }

//Compile / fullLinkJS / artifactPath := new File("E:/Steam/steamapps/common/Cookie Clicker/resources/app/mods/local/AutoCookie/")
//artifactPath in (Compile, fullLinkJS) := new File("E:/Steam/steamapps/common/Cookie Clicker/resources/app/mods/local/AutoCookie/")
//fastOptJS  / crossTarget := new File("E:/Steam/steamapps/common/Cookie Clicker/resources/app/mods/local/AutoCookie/")
//scalaJSLinkerOutputDirectory := new File("E:/Steam/steamapps/common/Cookie Clicker/resources/app/mods/local/AutoCookie/")


/*Seq(fastLinkJS, fullLinkJS) map { packageJSKey =>
  Compile / packageJSKey / crossTarget := new File("E:/Steam/steamapps/common/Cookie Clicker/resources/app/mods/local/AutoCookie/")
}*/

fastLinkJS / scalaJSLinkerOutputDirectory := new File("E:/Steam/steamapps/common/Cookie Clicker/resources/app/mods/local/AutoCookie/")