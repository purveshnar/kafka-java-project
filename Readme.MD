# Kafka
#For running this application. THis will send Hello world message to first.topic
1. Go to /Users/mineetnar/Desktop/Purvesh/kafka/kafka_2.13-2.8.0
2. zookeeper-server-start.sh config/zookeeper.properties
3. kafka-server-start.sh config/server.properties
4. kafka-topics.sh --zookeeper 127.0.0.1:2181 --topic first.topic --create --partitions 3 --replication-factor 1
5. kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic first.topic --group my-group-name
6. Run ProducerConfig.java 
   



# Detailed steps are below:
# Kafka is downloaded in /Users/mineetnar/Desktop/Purvesh/kafka

#Start zookeeper and Kafka broker in separate terminals
zookeeper-server-start.sh config/zookeeper.properties
kafka-server-start.sh config/server.properties
kafka-topics.sh --zookeeper 127.0.0.1:2181 --topic first.topic --create --partitions 3 --replication-factor 1
kafka-topics.sh --zookeeper 127.0.0.1:2181 --list

MacBook-Air:kafka_2.13-2.8.0 mineetnar$ kafka-topics.sh --zookeeper 127.0.0.1:2181 --topic first.topic --describe
Topic: first.topic	TopicId: T9i_sV8RRTGMq5iEvz_ziA	PartitionCount: 3	ReplicationFactor: 1	Configs: 
	Topic: first.topic	Partition: 0	Leader: 0	Replicas: 0	Isr: 0
	Topic: first.topic	Partition: 1	Leader: 0	Replicas: 0	Isr: 0
	Topic: first.topic	Partition: 2	Leader: 0	Replicas: 0	Isr: 0

MacBook-Air:kafka_2.13-2.8.0 mineetnar$ kafka-topics.sh -zookeeper 127.0.0.1:2181 --topic second.topic --create --partitions 6 --replication-factor 1
WARNING: Due to limitations in metric names, topics with a period ('.') or underscore ('_') could collide. To avoid issues it is best to use either, but not both.
Created topic second.topic.

MacBook-Air:kafka_2.13-2.8.0 mineetnar$ kafka-topics.sh --zookeeper 127.0.0.1:2181 --list
first.topic
second.topic

MacBook-Air:kafka_2.13-2.8.0 mineetnar$ kafka-topics.sh --zookeeper 127.0.0.1:2181 --topic second.topic --delete
Topic second.topic is marked for deletion.
Note: This will have no impact if delete.topic.enable is not set to true.

# KAFKA CONSOLE PRODUCER
=====================
MacBook-Air:kafka_2.13-2.8.0 mineetnar$ kafka-console-producer.sh --broker-list 127.0.0.1:9092 --topic first.topic
>this is first message
>THis is second message
>This is third message
>This is fourth message
>^C

MacBook-Air:kafka_2.13-2.8.0 mineetnar$ kafka-console-producer.sh --broker-list 127.0.0.1:9092 --topic first.topic --producer-property acks=all
>SOme message that is acked
>2nd message that is acked
>^C

Default config change: change in properties file

MacBook-Air:kafka_2.13-2.8.0 mineetnar$ kafka-topics.sh --zookeeper 127.0.0.1:2181 --topic first.topic --describe
Topic: first.topic	TopicId: T9i_sV8RRTGMq5iEvz_ziA	PartitionCount: 3	ReplicationFactor: 1	Configs: 
	Topic: first.topic	Partition: 0	Leader: 0	Replicas: 0	Isr: 0
	Topic: first.topic	Partition: 1	Leader: 0	Replicas: 0	Isr: 0
	Topic: first.topic	Partition: 2	Leader: 0	Replicas: 0	Isr: 0

# KAFKA CONSOLE CONSUMER
=====================
First produce the message:
MacBook-Air:kafka_2.13-2.8.0 mineetnar$ kafka-console-producer.sh --broker-list 127.0.0.1:9092 --topic first.topic
>hi
>

In another tab, consume the message:
MacBook-Air:kafka_2.13-2.8.0 mineetnar$ kafka-console-consumer.sh --bootstrap-server 127.0.0.1:9092 --topic first.topic
hi

Read all messages:
MacBook-Air:kafka_2.13-2.8.0 mineetnar$ kafka-console-consumer.sh --bootstrap-server 127.0.0.1:9092 --topic first.topic --from-beginning
Hello THis is my first message
no
hi
play play


If you have two instances of the same consumer with same group id, then messages will be split among both consumer.
MacBook-Air:kafka_2.13-2.8.0 mineetnar$ kafka-console-consumer.sh --bootstrap-server 127.0.0.1:9092 --topic first.topic --group my-group-name
Below screenshot shows three instances of Kafka consumers running and they share the messages produced.
￼

MacBook-Air:kafka_2.13-2.8.0 mineetnar$ kafka-consumer-groups.sh --bootstrap-server 127.0.0.1:9092 --list
my-group-name

# TO See how many messages are remaining to be read
=======================================
Here, the lag offset means that many messages are unread by the consumer


MacBook-Air:kafka_2.13-2.8.0 mineetnar$ kafka-consumer-groups.sh --bootstrap-server 127.0.0.1:9092 --describe --group my-group-name
Consumer group 'my-group-name' has no active members.
GROUP           TOPIC           PARTITION  CURRENT-OFFSET  LOG-END-OFFSET  LAG             CONSUMER-ID     HOST            CLIENT-ID
my-group-name   first.topic     0          3               3               0               -               -               -
my-group-name   first.topic     1          5               8               3               -               -               -
my-group-name   first.topic     2          3               7               4               -               -               -


MacBook-Air:kafka_2.13-2.8.0 mineetnar$ kafka-consumer-groups.sh --bootstrap-server 127.0.0.1:9092 --group my-group-name --reset-offsets --to-earliest --execute --topic first.topic
GROUP                          TOPIC                          PARTITION  NEW-OFFSET     
my-group-name                  first.topic                    0          3              
my-group-name                  first.topic                    1          2              
my-group-name                  first.topic                    2          1