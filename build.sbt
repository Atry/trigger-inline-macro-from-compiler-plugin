ThisBuild / scalaVersion := "0.18.1-RC1"

libraryDependencies += "ch.epfl.lamp" %% "dotty-compiler" % scalaVersion.value

Test / scalacOptions += raw"""-Xplugin:${(Compile / packageBin).value}"""
