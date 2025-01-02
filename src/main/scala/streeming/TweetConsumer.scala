package streaming

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
    try {
      while (true) {
        val records = consumer.poll(java.time.Duration.ofMillis(500))

        // Ensure records exist before processing
        if (!records.isEmpty) {
          records.records(KafkaConfig.topicName).asScala.foreach { record =>
            tweetCount += 1

            // Call TweetProcessor to process the data and perform sentiment analysis
            TweetProcessor.process(record.value()) // Ensure that this is a String
          }

          // Print the total number of tweets processed so far
          println(s"Total tweets processed: $tweetCount")
        }
      }
    } catch {
      case e: InterruptedException =>
        println("Kafka consumer interrupted.")
        consumer.close() // Gracefully close the consumer
    }
  }
}
