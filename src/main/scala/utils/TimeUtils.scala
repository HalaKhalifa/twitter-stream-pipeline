package utils

import java.time.format.DateTimeFormatter
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.Date

object TimeUtils {
  val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

  // Function to format the 'created_at' time from the tweet
  def formatTweetTime(createdAt: String): String = {
    // Parse the 'created_at' string into a LocalDateTime object using the correct format
    val tweetTime = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss Z yyyy").parse(createdAt)
    
    // Convert it to LocalDateTime and format it to the desired format
    val localDateTime = LocalDateTime.from(tweetTime)
    
    // Return the formatted time as a string
    localDateTime.format(formatter)
  }

  // Function to convert LocalDateTime to a proper Elasticsearch-compatible date format
  def convertToElasticsearchFormat(createdAt: String): String = {
    val tweetTime = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss Z yyyy").parse(createdAt)
    val localDateTime = LocalDateTime.from(tweetTime)
    val instant = localDateTime.toInstant(ZoneOffset.UTC)
    val date = Date.from(instant)
    
    // Return the string in Elasticsearch's expected format
    val esDateFormat = new java.text.SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy")
    esDateFormat.format(date)
  }
}
