package streaming

import org.apache.kafka.clients.consumer.KafkaConsumer
import scala.jdk.CollectionConverters._
import processing.TweetProcessor
import storage.ElasticsearchTweetStorage
import io.circe.Json

object TweetConsumer {

  def main(args: Array[String]): Unit = {
    // Load Kafka consumer configuration
    val consumer = new KafkaConsumer[String, String](KafkaConfig.getConsumerConfig())

    // Subscribe to the Kafka topic
    consumer.subscribe(List(KafkaConfig.topicName).asJava)

    var tweetCount = 0

    // Initialize Elasticsearch index
    ElasticsearchTweetStorage.initializeIndex()

    // Poll Kafka for messages
    try {
      while (true) {
        val records = consumer.poll(java.time.Duration.ofMillis(500))

        // Ensure records exist before processing
        if (!records.isEmpty) {
          records.records(KafkaConfig.topicName).asScala.foreach { record =>
            tweetCount += 1

            try {
              // Step 1: Process the tweet using TweetProcessor
              val processedTweet: Json = TweetProcessor.process(record.value())

              // Step 2: Store the processed tweet in Elasticsearch
              ElasticsearchTweetStorage.storeTweet(processedTweet)

              // Optional: Log success
              println(s"Processed and stored tweet #$tweetCount")
            } catch {
              case e: Exception =>
                println(s"Error processing/storing tweet: ${e.getMessage}")
            }
          }

          // Print the total number of tweets processed so far
          println(s"Total tweets processed: $tweetCount")
        }
      }
    } catch {
      case e: InterruptedException =>
        println("Kafka consumer interrupted.")
    } finally {
      consumer.close() // Gracefully close the consumer
      ElasticsearchTweetStorage.close() // Close Elasticsearch client
    }
  }
}