package config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class KafkaConsumerDemo {
    public static void main(String[] args) {

        //create consumer config
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "my-group-name");
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        //create consumer
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(properties);

        //subscribe to consumer
        kafkaConsumer.subscribe(Collections.singleton("first.topic"));

        //read data
        while(true) {
            ConsumerRecords<String, String> consumerRecords =  kafkaConsumer.poll(Duration.ofMillis(100));
            consumerRecords.forEach(record -> {
                System.out.println("record.key() = " + record.key());
                System.out.println("record.value() = " + record.value());
            });
        }
    }
}
