# Twitter Stream Processing Pipeline
A pipeline to ingest, process, store, and visualize tweets in real-time.
## Step 1: Start Zookeeper
##  Step 2: Start Kafka Server
## Step 3: Create the Kafka Topic After Kafka is up and running, create the tweets-topic with 3 partitions
kafka-topics.bat --create --topic tweets-topic --bootstrap-server localhost:9092 --partitions 3 --replication-factor 1
## Step 4: Run TweetProducer.scala
## Step 5: Run TweetConsumer.scala