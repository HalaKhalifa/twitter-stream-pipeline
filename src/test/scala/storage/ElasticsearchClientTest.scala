package storage

import org.scalatest.funsuite.AnyFunSuite
import org.elasticsearch.client.{RequestOptions, RestHighLevelClient}
import org.elasticsearch.client.indices.GetIndexRequest

class ElasticsearchClientTester extends AnyFunSuite {

  test("Create index with valid schema and verify existence") {
    val schema =
      """
        {
          "mappings": {
            "properties": {
              "id": { "type": "keyword" },
              "text": { "type": "text", "analyzer": "standard" },
              "created_at": { "type": "date", "format": "EEE MMM dd HH:mm:ss Z yyyy" },
              "coordinates": { "type": "geo_point" },
              "hashtags": { "type": "keyword" },
              "sentiment": { "type": "float" },
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

    val client: RestHighLevelClient = ElasticsearchClient.createClient()

    try {
      // Create the index
      ElasticsearchClient.createIndex(client, schema)

      // Verify index creation
      val getIndexRequest = new GetIndexRequest(ElasticsearchConfig.getIndexName)
      val indexExists = client.indices().exists(getIndexRequest, RequestOptions.DEFAULT)

      assert(indexExists, s"Index '${ElasticsearchConfig.getIndexName}' should exist.")
    } catch {
      case e: Exception =>
        fail(s"Exception occurred during test: ${e.getMessage}")
    } finally {
      ElasticsearchClient.closeClient(client)
    }
  }
}