package processing

import io.circe.parser.decode
import io.circe.generic.auto._

// Case classes for representing tweet data
case class Tweet(id: String, text: String, timestamp: String, latitude: Option[Double], longitude: Option[Double])
case class ProcessedTweet(
                           id: String,
                           text: String,
                           hashtags: Seq[String],
                           sentiment: String,
                           timestamp: String,
                           location: Option[(Double, Double)]
                         )

// Object that processes tweets
object TweetProcessor {

  // Method to process the raw tweet JSON
  def process(tweetJson: String): Either[String, ProcessedTweet] = {
    // Decode the raw tweet JSON string into a Tweet case class
    val maybeTweet = decode[Tweet](tweetJson)

    maybeTweet match {
      case Right(tweet) =>
        // Extract hashtags using the HashtagExtractor
        val hashtags = HashtagExtractor.extractHashtags(tweet.text)

        // Perform sentiment analysis using the SentimentAnalyzer
        val sentiment = SentimentAnalyzer.analyzeSentiment(tweet.text)

        // Handle location if available (latitude and longitude)
        val location = for {
          lat <- tweet.latitude
          lon <- tweet.longitude
        } yield (lat, lon)

        // Create a ProcessedTweet with enriched data
        val processedTweet = ProcessedTweet(
          id = tweet.id,
          text = tweet.text,
          hashtags = hashtags,
          sentiment = sentiment,
          timestamp = tweet.timestamp,
          location = location
        )

        Right(processedTweet)

      case Left(error) =>
        Left(s"Failed to decode tweet JSON: $error")
    }
  }
}
