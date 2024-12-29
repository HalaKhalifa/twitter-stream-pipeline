package processing

object TweetProcessorTest {
  def main(args: Array[String]): Unit = {
    // Sample tweet JSON string
    val testJson = """{
      "id": "1",
      "text": "I love Scala! #programming #scala",
      "timestamp": "2024-01-01T12:00:00Z",
      "latitude": 40.015,
      "longitude": -105.270
    }"""

    // Process the tweet using TweetProcessor
    val result = TweetProcessor.process(testJson)

    result match {
      case Right(processedTweet) =>
        // If processing is successful, print the processed tweet
        println("Processing successful!")
        println(s"Processed Tweet: ${processedTweet}")

      case Left(error) =>
        // If processing fails, print the error message
        println(s"Processing failed: $error")
    }
  }
}
