package utils

import io.circe._
import io.circe.parser._  // For parsing JSON

object TweetUtils {

  // Function to extract coordinates from the tweet JSON
  def extractCoordinates(tweetJson: String): Option[List[Double]] = {
    val decodedJson = parse(tweetJson).getOrElse(Json.Null)
    decodedJson.hcursor.downField("coordinates").downField("coordinates").as[List[Double]].toOption
  }
}
