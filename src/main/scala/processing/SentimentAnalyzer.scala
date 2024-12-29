package processing

object SentimentAnalyzer {

  // A set of positive words for sentiment analysis
  val positiveWords = Set(
    "love", "happy", "great", "awesome", "fantastic", "good", "joy", "exciting", "amazing", "beautiful",
    "wonderful", "positive", "excellent", "incredible", "delightful", "pleasure", "cheerful", "smile"
  )

  // A set of negative words for sentiment analysis
  val negativeWords = Set(
    "hate", "angry", "bad", "sad", "terrible", "awful", "worst", "depressed", "miserable", "angst", "annoyed",
    "disappointed", "frustrated", "upset", "rage", "negative", "pain", "sick"
  )

  // A set of neutral words
  val neutralWords = Set(
    "the", "is", "a", "on", "and", "for", "to", "of", "it", "this", "that", "in", "with", "as", "by", "from"
  )

  // Analyze sentiment based on positive and negative word counts
  def analyzeSentiment(text: String): String = {
    // Normalize the text by splitting it into words and converting them to lowercase
    val words = text.split("\\W+").map(_.toLowerCase)

    // Count positive and negative word occurrences
    val positiveCount = words.count(positiveWords.contains)
    val negativeCount = words.count(negativeWords.contains)

    // Determine sentiment based on word counts
    if (positiveCount > negativeCount) {
      "positive"
    } else if (negativeCount > positiveCount) {
      "negative"
    } else {
      "neutral"
    }
  }
}
