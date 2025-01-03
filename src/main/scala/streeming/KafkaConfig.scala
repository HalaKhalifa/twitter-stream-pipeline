package streaming

import java.util.Properties
import org.apache.kafka.common.serialization.{StringDeserializer, StringSerializer}

object KafkaConfig {
  private val bootstrapServers = "localhost:9092"
  val topicName = "Tweets"

  def getProducerConfig(): Properties = {
    val props = new Properties()
    props.put("bootstrap.servers", bootstrapServers)
    props.put("key.serializer", classOf[StringSerializer].getName)
    props.put("value.serializer", classOf[StringSerializer].getName)
    props.put("acks", "all") // Ensure data is acknowledged by all Kafka brokers
    props
  }

  def getConsumerConfig(): Properties = {
    val props = new Properties()
    props.put("bootstrap.servers", bootstrapServers)
    props.put("group.id", "tweet-consumer-group")
    props.put("key.deserializer", classOf[StringDeserializer].getName)
    props.put("value.deserializer", classOf[StringDeserializer].getName)
    props.put("auto.offset.reset", "earliest") // This ensures that the consumer starts from the earliest offset
    props
  }
}
