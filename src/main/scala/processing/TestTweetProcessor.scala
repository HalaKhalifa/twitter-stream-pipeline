//package processing
//
//object TestTweetProcessor {
//  def main(args: Array[String]): Unit = {
//    // Sample tweet in JSON format
//    val sampleTweet = """{
//                        |  "created_at": "Tue Dec 31 07:14:22 +0000 2013",
//                        |  "text": "renad #Boulder iam happy",
//                        |  "entities": {
//                        |    "hashtags": [{"text": "Boulder"}]
//                        |  }
//                        |}""".stripMargin
//
//    println(s"Processing Tweet: $sampleTweet")
//
//    // Extract hashtags
//    val hashtags = HashtagExtractor.extractHashtags(sampleTweet)
//    println(s"Extracted Hashtags: ${hashtags.mkString(", ")}")
//
//    // Perform sentiment analysis
//    val sentiment = SentimentAnalyzer.analyzeSentiment(sampleTweet)
//    println(s"Sentiment Analysis: $sentiment")
//  }
//}
