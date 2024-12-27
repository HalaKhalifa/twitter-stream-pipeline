package streeming

import java.util.Properties
import org.apache.kafka.common.serialization.{StringDeserializer, StringSerializer}

object KafkaConfig {

  private val bootstrapServers = "localhost:9092" // Adjust this to your Kafka brokers
  val topicName = "tweets-topic"           // Kafka topic name

  // Producer configuration
  def getProducerConfig(): Properties = {
    val props = new Properties()
    props.put("bootstrap.servers", bootstrapServers)
    props.put("key.serializer", classOf[StringSerializer].getName)
    props.put("value.serializer", classOf[StringSerializer].getName)
    props.put("acks", "all") // Ensure data is acknowledged by all Kafka brokers
    props
  }

  // Consumer configuration
  def getConsumerConfig(): Properties = {
    val props = new Properties()
    props.put("bootstrap.servers", bootstrapServers)
    props.put("group.id", "test-consumer-group") // Consumer group ID
    props.put("key.deserializer", classOf[StringDeserializer].getName)
    props.put("value.deserializer", classOf[StringDeserializer].getName)
    props.put("auto.offset.reset", "earliest") // To start reading from the earliest message if no offset is stored
    props
  }
}
