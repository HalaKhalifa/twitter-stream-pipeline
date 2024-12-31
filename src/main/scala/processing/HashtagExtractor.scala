package processing

object HashtagExtractor {

  // Extracts hashtags from tweet text and returns a list
  def extractHashtags(tweetText: String): List[String] = {
    // Regular expression to match hashtags
    val hashtagPattern = """#\w+""".r

    // Extract hashtags and return as a list
    hashtagPattern.findAllIn(tweetText).toList
  }
}
