package processing
object TestTweetProcessor {
  def main(args: Array[String]): Unit = {
    val sampleTweet =
      """Ringing in the #NewYear @BMoCA for their NYE at the Factory event! Surrounded by #art and Warholesque fun :) #Boulder"""

    TweetProcessor.process(sampleTweet)
  }
}
