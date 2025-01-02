name := "TwitterStreamPipeline"

version := "1.0"

scalaVersion := "2.12.18"

libraryDependencies ++= Seq(
  // Spark Core for general functionality
  "org.apache.spark" %% "spark-core" % "3.5.3",

  // Kafka clients for Kafka Producer and Consumer
  "org.apache.kafka" % "kafka-clients" % "3.5.0",

  // Spark SQL with Kafka integration
  "org.apache.spark" %% "spark-sql-kafka-0-10" % "3.5.0",

  // Elasticsearch High-Level REST Client
  "org.elasticsearch.client" % "elasticsearch-rest-high-level-client" % "7.17.10",

  // Testing library
  "org.scalatest" %% "scalatest" % "3.2.16" % Test,

  // Configuration library for managing application settings
  "com.typesafe" % "config" % "1.4.2",

  // JSON library for JSON parsing and serialization
  "org.json4s" %% "json4s-native" % "4.0.6",
  "org.scala-lang" % "scala-library" % "2.12.15",

  // Play JSON library for JSON support
  "com.typesafe.play" %% "play-json" % "2.10.0-RC5",
  "io.circe" %% "circe-core" % "0.14.1",
  "io.circe" %% "circe-parser" % "0.14.1",
  "io.circe" %% "circe-generic" % "0.14.1"


)
