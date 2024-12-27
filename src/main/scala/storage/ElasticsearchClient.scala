package storage

import org.apache.http.HttpHost
import org.elasticsearch.client.{RestClient, RestHighLevelClient, RequestOptions}
import org.elasticsearch.client.indices.CreateIndexRequest
import org.elasticsearch.xcontent.XContentType

object ElasticsearchClient {

  // Create a connection to Elasticsearch using the configuration
  def createClient(): RestHighLevelClient = {
    new RestHighLevelClient(
      RestClient.builder(
        new HttpHost(ElasticsearchConfig.host, ElasticsearchConfig.port, ElasticsearchConfig.scheme)
      )
    )
  }

  // Create an index in Elasticsearch using a predefined schema
  def createIndex(client: RestHighLevelClient, schema: String): Unit = {
    val request = new CreateIndexRequest(ElasticsearchConfig.getIndexName)
    request.source(schema, XContentType.JSON)
    client.indices().create(request, RequestOptions.DEFAULT)
    println(s"Index '${ElasticsearchConfig.getIndexName}' created successfully.")
  }

  // Close the client connection
  def closeClient(client: RestHighLevelClient): Unit = {
    client.close()
    println("Elasticsearch client closed.")
  }
}