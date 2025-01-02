package utils

import java.time.format.DateTimeFormatter
import java.time.LocalDateTime

object TimeUtils {
  val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

  def getCurrentTime: String = LocalDateTime.now.format(formatter)
}

