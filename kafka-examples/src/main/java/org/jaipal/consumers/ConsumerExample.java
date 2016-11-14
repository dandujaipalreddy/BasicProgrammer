package org.jaipal.consumers;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Map;
import java.util.Properties;


/**
 * Created by dreddy on 11/8/2016.
 */
public class ConsumerExample {

    private static Properties loadProperties() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "10.1.90.166:9092,10.1.90.167:9092,10.1.90.168:9092");
        properties.put("zookeeper.connect", "10.1.90.170:2181,10.1.90.85:2181");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("group.id", "test-consumer");
        properties.put("zookeeper.session.timeout.ms", "500");
        properties.put("zookeeper.sync.time.ms", "250");
        properties.put("auto.commit.interval.ms", "1000");
        properties.put("partition.assignment.strategy", "range");
        return properties;
    }

    public static void main(String[] args) {

        KafkaConsumer<String,String> kafkaConsumer=null;
        Properties props=loadProperties();
        kafkaConsumer=new KafkaConsumer<String, String>(props);
        kafkaConsumer.subscribe(String.valueOf(Arrays.asList("test-topic")));
        while(true){
            Map<String, ConsumerRecords<String, String>> records = kafkaConsumer.poll(200);
            System.out.println("KeySet"+records);
        }

    }
}
