libraryDependencies += "org.slf4j" % "slf4j-simple" % "1.7.10"

libraryDependencies += "org.webjars" % "angularjs" % "1.3.15"

libraryDependencies += "org.eclipse.jetty" % "jetty-server" % "8.1.16.v20140903"

lazy val root = (project in file("."))
  .enablePlugins(SbtWeb)

Revolver.settings

Revolver.reStartArgs ++= Seq("path=" + WebKeys.stagingDirectory.value, "port=$webServerPort$")

Revolver.reStart <<= Revolver.reStart.dependsOn(WebKeys.stage)

// don't use std error by default as this confuses Revolver
javaOptions in Revolver.reStart += "-Dorg.slf4j.simpleLogger.logFile=System.out"

Revolver.reLogTag := "$name$"
