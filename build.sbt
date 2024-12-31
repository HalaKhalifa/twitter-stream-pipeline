name := "TwitterStreamPipeline"

version := "1.0"

scalaVersion := "2.12.18"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "3.5.3",
  "org.apache.spark" %% "spark-sql" % "3.5.3",
  "org.apache.kafka" % "kafka-clients" % "3.5.0",
  "org.apache.spark" %% "spark-sql-kafka-0-10" % "3.5.0",
  "org.elasticsearch.client" % "elasticsearch-rest-high-level-client" % "7.17.10",
  "org.scalatest" %% "scalatest" % "3.2.16" % Test,
  "com.typesafe" % "config" % "1.4.2",
  "org.json4s" %% "json4s-native" % "4.0.6",
  "com.johnsnowlabs.nlp" %% "spark-nlp" % "4.2.8"
)

