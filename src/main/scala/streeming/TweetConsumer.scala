package streeming

import org.apache.kafka.clients.consumer.KafkaConsumer
import scala.jdk.CollectionConverters._
import processing.TweetProcessor // Import the TweetProcessor

object TweetConsumer {

  def main(args: Array[String]): Unit = {
    // Load Kafka consumer configuration
    val consumer = new KafkaConsumer[String, String](KafkaConfig.getConsumerConfig())

    // Subscribe to the Kafka topic (renad)
    consumer.subscribe(List(KafkaConfig.topicName).asJava)

    var tweetCount = 0

    // Poll Kafka for messages
    while (true) {
      val records = consumer.poll(java.time.Duration.ofMillis(500))
      records.records(KafkaConfig.topicName).asScala.foreach { record =>
        tweetCount += 1

        // Call TweetProcessor to process the data
        TweetProcessor.process(record.value())
      }

      // Print the total number of tweets received so far
      println(s"Total tweets processed: $tweetCount")
    }
  }
}
