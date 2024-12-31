package processing

import org.apache.spark.sql.SparkSession

object TweetProcessor {

  // Initialize SparkSession (required for Spark NLP)
  val spark = SparkSession.builder()
    .appName("TweetProcessing")
    .master("local[*]")  // Set to local for testing, update for production
    .getOrCreate()

  def process(tweet: String): Unit = {
    println(s"Processing tweet: $tweet")

    // Extract hashtags
    val hashtags = HashtagExtractor.extractHashtags(tweet)
    println(s"Extracted hashtags: $hashtags")

    // Perform sentiment analysis
    val sentiment = SentimentAnalyzer.analyzeSentiment(tweet)
    println(s"Sentiment: $sentiment")

    // You can now save this data to the storage (Elasticsearch, MongoDB, etc.)
    // Save to your storage system (Elasticsearch or MongoDB)
    // Example: saveToDatabase(tweet, hashtags, sentiment)
  }

  // Example function to save to a database (Elasticsearch or MongoDB)
  def saveToDatabase(tweet: String, hashtags: List[String], sentiment: String): Unit = {
    // Save the tweet data to your database
    println(s"Saving to database: Tweet: $tweet, Hashtags: $hashtags, Sentiment: $sentiment")
  }
}
