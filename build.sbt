name := "TwitterStreamPipeline"

version := "0.1"

scalaVersion := "2.13.12"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "3.5.3",
  "org.elasticsearch.client" % "elasticsearch-rest-high-level-client" % "7.17.10",
  "com.typesafe" % "config" % "1.4.2",
  "org.json4s" %% "json4s-native" % "4.0.6"
)