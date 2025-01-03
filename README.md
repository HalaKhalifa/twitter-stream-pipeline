# **Twitter Stream Processing Pipeline**
![Twitter Stream Processing](./assets/twitter_stream_processing_image.webp)


## **Overview**
The **Twitter Stream Processing Pipeline** is a robust system designed to handle streaming tweets, process and transform the data, and visualize insights through an interactive web application. This pipeline integrates real-time data ingestion, processing, and storage while leveraging Kafka, Elasticsearch, and Kibana.

### **Key Features:**
1. **Stream Ingestion:** Simulates the Twitter API to stream tweets into Kafka topics.
2. **Data Processing:** Extracts hashtags, performs sentiment analysis, and formats tweets for storage.
3. **Storage:** Indexes processed tweets in Elasticsearch for efficient querying and visualization.
4. **Web Visualization:** Provides a dashboard to analyze trends, sentiments, and geo-coordinates.

---
## **Requirements**

- **Scala Version:** 2.12.18
- **Kafka Version:** Compatible with Apache Kafka
- **Elasticsearch Version:** 8.x
- **Kibana Version:** 8.x
- **Python Version:** 3.8+
- **Libraries:**
  - **TextBlob** (for text processing and analysis)
  - **Flask** for the website view app.
---
## **Installation Steps**

1. **Clone the Repository:**
 ```bash
git clone https://github.com/HalaKhalifa/twitter-stream-pipeline.git
cd twitter-stream-pipeline
```

2. **Kafka Setup:**
   - Download and install Apache Kafka.

3. **Elasticsearch and Kibana Setup:**
   - Download and install Elasticsearch and Kibana.
---
---
## **Start Services**
### **Running Kafka**

#### For Windows:

1. **Start the Zookeeper server:**
    ```bash
    bin/zookeeper-server-start.bat config/zookeeper.properties
    ```

2. **Start the Kafka server:**
    ```bash
    bin/kafka-server-start.bat config/server.properties
    ```

3. **Create the tweets topic with 3 partitions:**
    ```bash
    bin/kafka-topics.bat --create --topic tweets --bootstrap-server localhost:9092 --partitions 3 --replication-factor 1
    ```

4. **Before Running Consumer:**
    Add consumer offset to save the offset of read documents, so if it doesn't start reading, it will start from the beginning.
    
    - Navigate to the Kafka bin directory:
      ```bash
      C:\kafka\bin\windows>
      ```
    - Reset offsets for the consumer group:
      ```bash
      kafka-consumer-groups.bat --bootstrap-server localhost:9092 --group test-consumer-group --reset-offsets --to-earliest --all-topics --execute
      ```
    - Start the consumer to read from the beginning:
      ```bash
      kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic tweets --from-beginning
      ```


#### For macOS:

1. **Start the Zookeeper server:**
    ```bash
    /opt/homebrew/bin/zookeeper-server-start /opt/homebrew/etc/zookeeper/zoo.cfg
    ```

2. **Start the Kafka server:**
    ```bash
    /opt/homebrew/bin/kafka-server-start /opt/homebrew/etc/kafka/server.properties
    ```

3. **Create the tweets topic with 3 partitions:**
    ```bash
    /opt/homebrew/bin/kafka-topics --create --topic tweets --bootstrap-server localhost:9092 --partitions 3 --replication-factor 1
    ```

4. **Before Running Consumer:**
    - Reset offsets for the consumer group:
      ```bash
      /opt/homebrew/bin/kafka-consumer-groups --bootstrap-server localhost:9092 --group test-consumer-group --reset-offsets --to-earliest --all-topics --execute
      ```
    - Start the consumer to read from the beginning:
      ```bash
      /opt/homebrew/bin/kafka-console-consumer --bootstrap-server localhost:9092 --topic tweets --from-beginning
      ```

---
### **Running Elasticsearch and Kibana**

#### For Windows:

1. **Start Elasticsearch:**
    ```bash
    bin\elasticsearch.bat
    ```
    Verify Elasticsearch is running by visiting: [http://localhost:9200](http://localhost:9200)

2. **Start Kibana:**
    ```bash
    bin\kibana.bat
    ```
    Access Kibana at: [http://localhost:5601](http://localhost:5601)


#### For macOS:

1. **Start Elasticsearch:**
    ```bash
    brew services start elastic/tap/elasticsearch-full
    ```
    Verify Elasticsearch is running by visiting: [http://localhost:9200](http://localhost:9200)

2. **Start Kibana:**
    ```bash
    brew services start elastic/tap/kibana-full
    ```
    Access Kibana at: [http://localhost:5601](http://localhost:5601)

---
---

### **Running the Pipeline**
1. **Compile and update the SBT project:**
  ```bash
    sbt clean compile
    sbt update
  ```
2. **Start Kafka Producer** 'Stream tweets into Kafka':
 ```bash
sbt "runMain streaming.TweetProducer"
 ```

3. **Start Kafka Consumer** 'Process tweets from Kafka':
```bash
sbt "runMain streaming.TweetConsumer"
 ```
---
## Pipeline Overview

Below is a figure illustrating the pipeline:

![Pipeline Overview](./assets/pipeline_overview_image.png)

### Pipeline Components:
1. **Kafka Producer:** Streams tweets to Kafka topics.
2. **Kafka Consumer:** Processes tweets (hashtags, sentiment analysis).
3. **Elasticsearch:** Stores processed tweets for search and analytics.
4. **Kibana Dashboard:** Visualizes trends, sentiments, and geo-data.

---
---
## Contributors
- **Raghad:** Kafka Producer/Consumer
- **Renad:** Tweet Processing and Sentiment Analysis
- **Hala:** Elasticsearch Integration and Kibana.