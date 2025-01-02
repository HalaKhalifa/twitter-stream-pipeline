package producer

import streaming.{KafkaConfig, TweetProducer}
import org.apache.kafka.clients.consumer.KafkaConsumer
import scala.jdk.CollectionConverters._
import java.time.Duration

object ProducerTest {
  def main(args: Array[String]): Unit = {
    // Producer sends messages
    println("Starting Producer Test...")
    TweetProducer.main(Array())

    println("Validating messages in Kafka...")

    // Kafka consumer setup for validation
    val props = KafkaConfig.getConsumerConfig()
    val consumer = new KafkaConsumer[String, String](props)
    consumer.subscribe(List(KafkaConfig.topicName).asJava)

    val records = consumer.poll(Duration.ofSeconds(10))

    if (!records.isEmpty) {
      records.records(KafkaConfig.topicName).asScala.foreach { record =>
        println(s"Consumed message: ${record.value()}")
      }
      println(s"Producer Test Passed: ${records.count()} messages found.")
    } else {
      println("Producer Test Failed: No messages found.")
    }

    consumer.close()
  }
}
