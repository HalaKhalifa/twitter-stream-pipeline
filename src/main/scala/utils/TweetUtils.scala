package utils

object TweetUtils {

  def storeInElasticsearch(tweetJson: String, sentiment: String, hashtags: List[String]): Unit = {
    // Implement storing tweet data in Elasticsearch (if needed)
    println(s"Storing tweet in Elasticsearch: $tweetJson, Sentiment: $sentiment, Hashtags: ${hashtags.mkString(", ")}")
  }
}
