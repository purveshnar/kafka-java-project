package config;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class KafkaProducerDemoWithCallback {
    public static void main(String[] args) {
        /**create producer properties*/

        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        /**create the producer*/
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        /**send data*/
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>("first.topic", "Hello world From Callback");
        producer.send(producerRecord, new Callback() {
            @Override
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                //executes everytime record is sent or exception is thrown
                if (e == null) {
                    //no error
                    System.out.println("Topic = " + recordMetadata.topic());
                    System.out.println("recordMetadata.partition() = " + recordMetadata.partition());
                    System.out.println("recordMetadata.offset() = " + recordMetadata.offset());
                    System.out.println("recordMetadata.timestamp() = " + recordMetadata.timestamp());
                } else {

                }
            }
        });

        /**flush data*/
        producer.close(); // or producer.flush();
    }
}
