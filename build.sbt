name := "fs2-demo"
version := "0.1.0-SNAPSHOT"
scalaVersion in ThisBuild :=  "2.12.4"

libraryDependencies += "co.fs2" %% "fs2-core" % "0.10.2"
libraryDependencies += "co.fs2" %% "fs2-io" % "0.10.2"
libraryDependencies += "org.typelevel" %% "cats-effect" % "0.9"

// fork in run := true
javaOptions += "-Xmx64m"
javaOptions += "-agentpath:/Users/bill.carlson/Projects/cotiviti/yourkit/mac/libyjpagent.jnilib=sessionname=fs2-demo"
