package processing
import scala.sys.process._
import io.circe._  // Circe JSON library
import io.circe.parser._  // For parsing JSON

object TweetProcessor {

  // Sentiment analysis using Python script
  def analyzeSentimentWithPython(tweet: String): Json = {
    try {
      // Update the path to the Python script
      val process = Process(Seq("python", "C:/Users/ktech/Desktop/twitter-stream-pipeline/python/sentiment_analysis.py", tweet))
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

  // Process a tweet and return the sentiment analysis result and hashtags
  def process(tweetJson: String): Unit = {
    // Parse tweet JSON to extract text
    val tweetText = getTweetText(tweetJson)

    // Extract hashtags
    val hashtags = extractHashtags(tweetText)
    println(s"Extracted hashtags: ${hashtags.mkString(", ")}")

    // Get sentiment analysis result from Python
    val sentimentResult = getSentiment(tweetText)
    println(s"Sentiment analysis result: $sentimentResult")
  }

  // Parse the tweet JSON and extract the text using Circe
  def getTweetText(json: String): String = {
    // Using Circe to parse JSON and extract the tweet text
    val decodedJson = parse(json).getOrElse(Json.Null)
    decodedJson.hcursor.downField("text").as[String].getOrElse("")
  }
}
