package config;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class KafkaProducerDemo {
    public static void main(String[] args) {
        /**create producer properties*/

        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        /**create the producer*/
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        /**send data*/
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>("first.topic", "Hello world");
        producer.send(producerRecord);

        /**flush data*/
        producer.close(); // or producer.flush();
    }
}
