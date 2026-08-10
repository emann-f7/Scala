name := "inventory-manager"
version := "1.0"
scalaVersion := "2.13.14"

libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick" % "3.5.1",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.5.1",
  "com.h2database" % "h2" % "2.2.224",
  "ch.qos.logback" % "logback-classic" % "1.5.6"
)