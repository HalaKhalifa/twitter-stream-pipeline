package utils

import io.circe._
import io.circe.parser._

object TweetUtils {

  // Function to extract coordinates from the tweet JSON and format them as geo_point
  def extractCoordinates(tweetJson: String): Option[Json] = {
    val decodedJson = parse(tweetJson).getOrElse(Json.Null)
    val coordinates = decodedJson.hcursor
      .downField("coordinates")
      .downField("coordinates")
      .as[List[Double]]
      .toOption

    // Format the coordinates as geo_point if they exist
    coordinates.map {
      case List(lon, lat) =>
        Json.obj(
          "lat" -> Json.fromDoubleOrNull(lat),
          "lon" -> Json.fromDoubleOrNull(lon)
        )
      case _ => Json.Null
    }
  }
}
