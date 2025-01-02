package processing

import scala.sys.process._
import io.circe._  // Circe JSON library
import io.circe.parser._  // For parsing JSON
import io.circe.syntax._  // For converting to JSON
import storage.ElasticsearchTweetStorage  // Import Elasticsearch storage
import utils.TweetUtils
import utils.TimeUtils

object TweetProcessor {

  // Sentiment analysis using Python script
  def analyzeSentimentWithPython(tweet: String): Json = {
    try {

      val process = Process(Seq("python", "python/sentiment_analysis.py", tweet))
      val result = process.!!  // Capture output of the Python script
      parse(result).getOrElse(Json.Null)  // Parse the result into Circe JSON
    } catch {
      case e: Exception =>
        println(s"Error running sentiment analysis script: ${e.getMessage}")
        Json.Null  // Return empty JSON in case of error
    }
  }

  // Function to get sentiment using Python-based sentiment analysis
  def getSentiment(tweet: String): Map[String, Any] = {
    // Perform sentiment analysis using the Python script
    val sentimentResult = analyzeSentimentWithPython(tweet)

    // Extract sentiment details from the result
    Map(
      "sentiment" -> sentimentResult.hcursor.downField("sentiment").as[String].getOrElse("unknown"),
      "polarity" -> sentimentResult.hcursor.downField("polarity").as[Double].getOrElse(0.0),
      "subjectivity" -> sentimentResult.hcursor.downField("subjectivity").as[Double].getOrElse(0.0)
    )
  }

  // Extract hashtags from a tweet
  def extractHashtags(text: String): List[String] = {
    val hashtagPattern: scala.util.matching.Regex = "#(\\w+)".r
    hashtagPattern.findAllIn(text).toList
  }
  // Convert sentiment polarity to human-readable sentiment
  def getHumanReadableSentiment(polarity: Double): String = {
    if (polarity > 0.1) "positive"
    else if (polarity < -0.1) "negative"
    else "neutral"
  }
  // Process a tweet and return the enriched result
  def process(tweetJson: String): Json = {
    // Parse tweet JSON to extract text
    val tweetText = getTweetText(tweetJson)

    // Extract hashtags
    val hashtags = extractHashtags(tweetText)
    println(s"Extracted hashtags: ${hashtags.mkString(", ")}")

    // Get sentiment analysis result from Python
    val sentimentResult = getSentiment(tweetText)
    val sentiment = getHumanReadableSentiment(sentimentResult("polarity").asInstanceOf[Double])
    println(s"Sentiment analysis result: $sentiment")
    
    // Extract coordinates from the tweet
    val coordinates = TweetUtils.extractCoordinates(tweetJson)
    println(s"Extracted coordinates: ${coordinates.getOrElse("No coordinates")}")

    // Get the created_at timestamp and format it
    val createdAt = parse(tweetJson).getOrElse(Json.Null).hcursor.downField("created_at").as[String].getOrElse("")
    val formattedTime = TimeUtils.formatTweetTime(createdAt)
    println(s"Formatted created_at: $formattedTime")

    // Enrich the original JSON with sentiment, hashtags, and coordinates
    parse(tweetJson).getOrElse(Json.Null).mapObject(obj =>
      obj.add("sentiment", Json.fromString(sentiment))
        .add("hashtags", Json.fromValues(hashtags.map(Json.fromString)))
        .add("coordinates", Json.fromValues(coordinates.getOrElse(List.empty).map(Json.fromDoubleOrNull)))
        .add("created_at", Json.fromString(formattedTime))
    )
  
  }
  // Parse the tweet JSON and extract the text using Circe
  def getTweetText(json: String): String = {
    // Using Circe to parse JSON and extract the tweet text
    val decodedJson = parse(json).getOrElse(Json.Null)
    decodedJson.hcursor.downField("text").as[String].getOrElse("")
  }
}