package processing

object HashtagExtractor {
  // Extract hashtags from the tweet text
  def extractHashtags(text: String): Seq[String] = {
    // Regular expression to find hashtags
    val hashtagPattern = """#(\w+)""".r
    // Find all hashtags and return them as a sequence of strings
    hashtagPattern.findAllIn(text).matchData.map(_.group(1)).toSeq
  }
}
