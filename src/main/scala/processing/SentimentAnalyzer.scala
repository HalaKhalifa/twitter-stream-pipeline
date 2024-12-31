package processing

import com.johnsnowlabs.nlp.base.DocumentAssembler
import com.johnsnowlabs.nlp.pretrained.PretrainedPipeline
import org.apache.spark.sql.SparkSession

object SentimentAnalyzer {

  // Initialize Spark NLP pipeline
  val spark = SparkSession.builder()
    .appName("TweetSentimentAnalysis")
    .master("local[*]")
    .config("spark.serializer", "org.apache.spark.serializer.KryoSerializer") // Required for Spark NLP
    .getOrCreate()

  // Load pre-trained sentiment analysis model from Spark NLP
  val pipeline = PretrainedPipeline("analyze_sentiment", lang = "en")

  // Perform sentiment analysis on tweet text and return sentiment label
  def analyzeSentiment(tweetText: String): String = {
    try {
      val result = pipeline.annotate(tweetText)
      result.getOrElse("sentiment", Seq("neutral")).headOption.getOrElse("neutral")
    } catch {
      case e: Exception =>
        println(s"Error during sentiment analysis: ${e.getMessage}")
        "neutral" // Default fallback sentiment
    }
  }
}
