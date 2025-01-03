package streaming

import org.apache.kafka.clients.consumer.KafkaConsumer
import scala.jdk.CollectionConverters._
import processing.TweetProcessor
import storage.ElasticsearchTweetStorage
import io.circe.Json

object TweetConsumer {

  def main(args: Array[String]): Unit = {
    val consumer = new KafkaConsumer[String, String](KafkaConfig.getConsumerConfig())

    consumer.subscribe(List(KafkaConfig.topicName).asJava)

    var tweetCount = 0

    ElasticsearchTweetStorage.initializeIndex()

    // Poll Kafka for messages
    try {
      while (true) {
        val records = consumer.poll(java.time.Duration.ofMillis(500))

        if (!records.isEmpty) {
          records.records(KafkaConfig.topicName).asScala.foreach { record =>
            tweetCount += 1

            try {
              val processedTweet: Json = TweetProcessor.process(record.value())

              ElasticsearchTweetStorage.storeTweet(processedTweet)

              println(s"Processed and stored tweet #$tweetCount")
            } catch {
              case e: Exception =>
                println(s"Error processing/storing tweet: ${e.getMessage}")
            }
          }

          println(s"Total tweets processed: $tweetCount")
        }
      }
    } catch {
      case e: InterruptedException =>
        println("Kafka consumer interrupted.")
    } finally {
      consumer.close()
      ElasticsearchTweetStorage.close() 
    }
  }
}