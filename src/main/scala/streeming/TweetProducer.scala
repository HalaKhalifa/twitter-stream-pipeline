package streaming

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord, RecordMetadata}
import scala.io.Source
import scala.util.{Try, Using}

object TweetProducer {

  def main(args: Array[String]): Unit = {
    // Load producer configuration
    val producerProps = KafkaConfig.getProducerConfig()
    val topic = KafkaConfig.topicName  // Renamed to 'renad' topic

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

            // Send the record with a callback for handling success or failure
            producer.send(record, (metadata: RecordMetadata, exception: Exception) => {
              if (exception != null) {
                println(s"Error sending message: ${exception.getMessage}")
              } else {
                println(s"Sent tweet to topic $topic: $line")
                println(s"Message sent to partition: ${metadata.partition()}, offset: ${metadata.offset()}")
              }
            })

            tweetCount += 1

            // Delay between sends
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

    // Close the producer after sending messages
    producer.close()
  }
}
