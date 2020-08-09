ThisBuild / scalaVersion := "0.27.0-bin-20200731-bb23fea-NIGHTLY"

libraryDependencies += "ch.epfl.lamp" %% "dotty-compiler" % scalaVersion.value

Test / scalacOptions += raw"""-Xplugin:${(Compile / packageBin).value}"""

Test / scalacOptions += "-Xprint:all"