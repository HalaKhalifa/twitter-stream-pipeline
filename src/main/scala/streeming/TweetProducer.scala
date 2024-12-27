package streeming

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import scala.io.Source
import scala.util.{Try, Using}

object TweetProducer {

  def main(args: Array[String]): Unit = {
    // Load producer configuration
    val producerProps = KafkaConfig.getProducerConfig()
    val topic = KafkaConfig.topicName

    // Initialize Kafka producer
    val producer = new KafkaProducer[String, String](producerProps)

    // Path to the JSON file
    val jsonFilePath = "src/data/rawData/boulder_flood_geolocated_tweets.json"

    var tweetCount = 0

    // Read the JSON file and send messages to Kafka
    Try {
      Using.resource(Source.fromFile(jsonFilePath)) { source =>
        source.getLines().foreach { line =>
          Try {
            val record = new ProducerRecord[String, String](topic, null, line)

            producer.send(record)
            tweetCount += 1
            println(s"Sent tweet to topic $topic: $line")

            // delay between sends
            Thread.sleep(300)
          }.recover {
            case ex: Exception =>
              println(s"Error processing tweet: ${ex.getMessage}")
          }
        }
      }
    }.recover {
      case ex: Exception =>
        println(s"Error occurred while reading file or sending messages: ${ex.getMessage}")
    }
    println(s"Transmission completed. Total tweets sent: $tweetCount")
    producer.close()
  }
}
