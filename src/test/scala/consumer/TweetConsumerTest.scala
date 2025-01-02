package consumer

import streaming.{KafkaConfig, TweetConsumer}
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import scala.util.Try

object ConsumerTest {
  def main(args: Array[String]): Unit = {
    // Create Kafka producer for test messages
    println("Starting Consumer Test...")
    val props = KafkaConfig.getProducerConfig()
    val producer = new KafkaProducer[String, String](props)
    val topic = KafkaConfig.topicName

    val testMessage =
      """{
        |"created_at":"Tue Dec 31 23:18:42 +0000 2013",
        |"id":418159307403063296,
        |"id_str":"418159307403063296",
        |"text":"Walkies #boulder http://t.co/p0nra3MMSi",
        |"truncated":false,
        |"entities":{"hashtags":[{"text":"boulder","indices":[8,16]}],"symbols":[],"user_mentions":[],"urls":[{"url":"http://t.co/p0nra3MMSi","expanded_url":"http://instagram.com/p/imqs4hBA0D/","display_url":"instagram.com/p/imqs4hBA0D/","indices":[17,39]}]},
        |"source":"<a href=\"http://instagram.com\" rel=\"nofollow\">Instagram</a>",
        |"user":{"id":22428459,"id_str":"22428459","name":"Ben Smith","screen_name":"bencgsmith","location":"Australia","description":"Englishman abroad. Freelance Creative Director in Sydney. Past 72andSunny, Clemenger BBDO, CP+B, R/GA NYC, Droga5 SYD. Tweets about Liverpool FC or advertising.","url":"https://t.co/Xg34KVm4LY","entities":{"url":{"urls":[{"url":"https://t.co/Xg34KVm4LY","expanded_url":"http://mrbensmith.com","display_url":"mrbensmith.com","indices":[0,23]}]},"description":{"urls":[]}},"protected":false,"followers_count":907,"friends_count":726,"listed_count":19,"created_at":"Mon Mar 02 00:34:46 +0000 2009","favourites_count":344,"utc_offset":39600,"time_zone":"Sydney","geo_enabled":true,"verified":false,"statuses_count":3649,"lang":"en","contributors_enabled":false,"is_translator":false,"is_translation_enabled":false,"profile_background_color":"C0DEED","profile_background_image_url":"http://pbs.twimg.com/profile_background_images/305250421/IMG_4957.JPG","profile_background_image_url_https":"https://pbs.twimg.com/profile_background_images/305250421/IMG_4957.JPG","profile_background_tile":true,"profile_image_url":"http://pbs.twimg.com/profile_images/490150690908807168/4C9Fkaq7_normal.jpeg","profile_image_url_https":"https://pbs.twimg.com/profile_images/490150690908807168/4C9Fkaq7_normal.jpeg","profile_link_color":"0084B4","profile_sidebar_border_color":"C0DEED","profile_sidebar_fill_color":"DDEEF6","profile_text_color":"333333","profile_use_background_image":true,"has_extended_profile":false,"default_profile":false,"default_profile_image":false,"following":false,"follow_request_sent":false,"notifications":false,"translator_type":"none"},
        |"geo":null,
        |"coordinates":null,
        |"place":null,
        |"contributors":null,
        |"is_quote_status":false,
        |"retweet_count":0,
        |"favorite_count":0,
        |"favorited":false,
        |"retweeted":false,
        |"possibly_sensitive":false,
        |"lang":"en"
        |}""".stripMargin

    // Send the test message to Kafka
    Try {
      val record = new ProducerRecord[String, String](topic, null, testMessage)
      producer.send(record)
      println(s"Sent test message to topic $topic: $testMessage")
    }.recover {
      case ex: Exception => println(s"Failed to send message: ${ex.getMessage}")
    }

    producer.close()

    // Run the consumer to process the test message
    println("Running the consumer to process the test message...")
    TweetConsumer.main(Array())

    println("Consumer Test Completed. Check logs for message processing results.")
  }
}
