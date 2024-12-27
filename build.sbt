name := "TwitterStreamPipeline"

version := "0.1"

scalaVersion := "2.13.12"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "3.5.3",
  "org.apache.spark" %% "spark-streaming" % "3.4.0",
  "org.elasticsearch.client" % "elasticsearch-rest-high-level-client" % "7.17.9",
  "com.typesafe" % "config" % "1.4.2",
  "org.json4s" %% "json4s-native" % "4.0.6"
)
libraryDependencies += "org.apache.kafka" % "kafka-clients" % "3.5.0"
// https://mvnrepository.com/artifact/org.apache.spark/spark-sql-kafka-0-10
libraryDependencies += "org.apache.spark" %% "spark-sql-kafka-0-10" % "3.5.0"