package storage

object ElasticsearchConfig {
  val host: String = "localhost"
  val port: Int = 9200
  val scheme: String = "http"
  val tweetIndex: String = "twitter-stream"

  def getIndexName: String = tweetIndex
}