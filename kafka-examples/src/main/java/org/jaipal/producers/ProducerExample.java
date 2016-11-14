package org.jaipal.producers;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.log4j.Logger;

import java.util.Properties;
import java.util.UUID;

/**
 * Created by dreddy on 11/8/2016.
 */
public class ProducerExample {
    private static Logger logger = Logger.getLogger(ProducerExample.class);

    private static Properties loadProperties() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "10.1.90.166:9092,10.1.90.167:9092,10.1.90.168:9092");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("acks", "all");
        properties.put("retries", 0);
        properties.put("batch.size", 16384);
        //  properties.put("linger.ms", 1);
        properties.put("buffer.memory", 33554432);
        // properties.put("partitioner.class", "producer.SimplePartitioner");
        properties.put("client.id", "camus");
        return properties;
    }


    public static void main(String[] args) throws InterruptedException {
        KafkaProducer<String, Object> kafkaProducer = null;
        Properties properties = loadProperties();
        kafkaProducer = new KafkaProducer<String, Object>(properties);
        for (int i = 0; i < 10; i++) {
            ProducerRecord<String, Object> pr = new ProducerRecord<String, Object>("test-topic", String.format("%s is the UUID", UUID.randomUUID()));
            kafkaProducer.send(pr, new Callback() {
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if (e != null) {
                        logger.error("Error in call back " + e);
                    }
                }
            });
        }
        Thread.sleep(100000);
    }

}
