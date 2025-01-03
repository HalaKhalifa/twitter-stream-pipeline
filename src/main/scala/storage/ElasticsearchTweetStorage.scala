package storage

import org.elasticsearch.client.{RequestOptions, RestHighLevelClient}
import org.elasticsearch.action.index.IndexRequest
import org.elasticsearch.action.index.IndexResponse
import org.elasticsearch.xcontent.XContentType
import io.circe.Json

object ElasticsearchTweetStorage {

  val schema: String =
    """
      {
        "mappings": {
            "properties": {
            "id": { "type": "keyword" },
            "text": { "type": "text", "analyzer": "standard" },
            "created_at": { "type": "date", "format": "EEE MMM dd HH:mm:ss Z yyyy" },
            "coordinates": { "type": "geo_point" },
            "hashtags": { "type": "keyword" },
            "sentiment": { "type": "keyword" },
            "user": {
                "properties": {
                "id": { "type": "keyword" },
                "name": { "type": "text", "analyzer": "standard" },
                "screen_name": { "type": "keyword" }
                    }
                }
            }
        }
      }
    """

  private val client: RestHighLevelClient = ElasticsearchClient.createClient()

  def initializeIndex(): Unit = {
    try {
      ElasticsearchClient.createIndex(client, schema)
    } catch {
      case e: Exception =>
        println(s"Error initializing index: ${e.getMessage}")
    }
  }

  def storeTweet(tweetJson: Json): Unit = {
    try {
      val request = new IndexRequest(ElasticsearchConfig.getIndexName)
      request.source(tweetJson.noSpaces, XContentType.JSON)
      val response: IndexResponse = client.index(request, RequestOptions.DEFAULT)
      println(s"Tweet stored with ID: ${response.getId}")
    } catch {
      case e: Exception =>
        println(s"Error storing tweet: ${e.getMessage}")
    }
  }

  def close(): Unit = {
    ElasticsearchClient.closeClient(client)
  }
}