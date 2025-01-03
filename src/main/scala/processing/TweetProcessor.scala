package processing

import scala.sys.process._
import io.circe._ 
import io.circe.parser._
import io.circe.syntax._  // for converting to JSON
import storage.ElasticsearchTweetStorage
import utils.TweetUtils
import utils.TimeUtils

object TweetProcessor {

  // Sentiment analysis
  def analyzeSentimentWithPython(tweet: String): Json = {
    try {

      val process = Process(Seq("python", "python/sentiment_analysis.py", tweet))
      val result = process.!!  // Capture output of the Python script
      parse(result).getOrElse(Json.Null)  // Parse the result into Circe JSON
    } catch {
      case e: Exception =>
        println(s"Error running sentiment analysis script: ${e.getMessage}")
        Json.Null
    }
  }

  // Use sentiment_analysis.py
  def getSentiment(tweet: String): Map[String, Any] = {
    val sentimentResult = analyzeSentimentWithPython(tweet)

    Map(
      "sentiment" -> sentimentResult.hcursor.downField("sentiment").as[String].getOrElse("unknown"),
      "polarity" -> sentimentResult.hcursor.downField("polarity").as[Double].getOrElse(0.0),
      "subjectivity" -> sentimentResult.hcursor.downField("subjectivity").as[Double].getOrElse(0.0)
    )
  }

  def extractHashtags(text: String): List[String] = {
    val hashtagPattern: scala.util.matching.Regex = "#(\\w+)".r // .r to extract all hashtags
    hashtagPattern.findAllIn(text).toList
  }

  def getHumanReadableSentiment(polarity: Double): String = {
    if (polarity > 0.1) "positive"
    else if (polarity < -0.1) "negative"
    else "neutral"
  }

  def process(tweetJson: String): Json = {

    val tweetText = getTweetText(tweetJson)

    val hashtags = extractHashtags(tweetText)
    println(s"Extracted hashtags: ${hashtags.mkString(", ")}")

    val sentimentResult = getSentiment(tweetText)
    val sentiment = getHumanReadableSentiment(sentimentResult("polarity").asInstanceOf[Double])
    println(s"Sentiment analysis result: $sentiment")
    
    val geoPoint = TweetUtils.extractCoordinates(tweetJson).getOrElse(Json.Null)
    println(s"Formatted coordinates: $geoPoint")

    // Get the created_at timestamp and format it for Elasticsearch
    val createdAt = parse(tweetJson).getOrElse(Json.Null).hcursor.downField("created_at").as[String].getOrElse("")
    val formattedTime = TimeUtils.convertToElasticsearchFormat(createdAt)
    println(s"Formatted created_at for Elasticsearch: $formattedTime")

    // Add the sentiment, hashtags, coordinates and time to the original JSON
    parse(tweetJson).getOrElse(Json.Null).mapObject(obj =>
      obj.add("sentiment", Json.fromString(sentiment))
        .add("hashtags", Json.fromValues(hashtags.map(Json.fromString)))
        .add("coordinates", geoPoint)
        .add("created_at", Json.fromString(formattedTime))
    )
  
  }
  // Parse tweet JSON to extract text
  def getTweetText(json: String): String = {
    val decodedJson = parse(json).getOrElse(Json.Null)
    decodedJson.hcursor.downField("text").as[String].getOrElse("")
  }
}