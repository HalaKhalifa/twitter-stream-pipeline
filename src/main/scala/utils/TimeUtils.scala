package utils

import java.time.format.DateTimeFormatter
import java.time.LocalDateTime

object TimeUtils {
  val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

  // Function to format the 'created_at' time from the tweet
  def formatTweetTime(createdAt: String): String = {
    // Parse the 'created_at' string into a LocalDateTime object
    val tweetTime = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss Z yyyy").parse(createdAt)
    // Format it to the desired format
    LocalDateTime.from(tweetTime).format(formatter)
  }
}