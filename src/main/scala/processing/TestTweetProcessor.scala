package processing

object TestTweetProcessor {
  def main(args: Array[String]): Unit = {
    val sampleTweet = """{
                        |  "created_at": "Tue Dec 31 07:14:22 +0000 2013",
                        |  "text": "renad #Boulder iam happy",
                        |  "entities": {
                        |    "hashtags": [{"text": "Boulder"}]
                        |  }
                        |}""".stripMargin

    println(s"Processing Tweet: $sampleTweet")

    // Extract hashtags
    val hashtags = TweetProcessor.extractHashtags(sampleTweet)
    println(s"Extracted Hashtags: ${hashtags.mkString(", ")}")

    // Perform sentiment analysis
    val sentiment = TweetProcessor.getSentiment(sampleTweet)
    println(s"Sentiment Analysis: $sentiment")
  }
}