package utils

import java.time.format.DateTimeFormatter
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.Date

object TimeUtils {
  val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

  def formatTweetTime(createdAt: String): String = {
    val tweetTime = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss Z yyyy").parse(createdAt)
    
    val localDateTime = LocalDateTime.from(tweetTime)
    
    localDateTime.format(formatter)
  }

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
