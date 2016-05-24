package consumer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.avro.generic.GenericRecord;

import Serializers.AvroSupport;
import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

public class AvroConsumerExample {

	private final ConsumerConnector consumer;
	private final String topic;

	public AvroConsumerExample(String zookeeper, String groupId, String topic) {
		Properties props = new Properties();
		props.put("zookeeper.connect", zookeeper);
		props.put("group.id", groupId);
		props.put("zookeeper.session.timeout.ms", "500");
		props.put("zookeeper.sync.time.ms", "250");
		props.put("auto.commit.interval.ms", "1000");

		consumer = Consumer.createJavaConsumerConnector(new ConsumerConfig(props));
		this.topic = topic;
	}

	public void testConsumer() {
		Map<String, Integer> topicCount = new HashMap<>();
		topicCount.put(topic, 1);

		Map<String, List<KafkaStream<byte[], byte[]>>> consumerStreams = consumer.createMessageStreams(topicCount);
		List<KafkaStream<byte[], byte[]>> streams = consumerStreams.get(topic);
		for (final KafkaStream stream : streams) {
			ConsumerIterator<byte[], byte[]> it = stream.iterator();
			while (it.hasNext()) {
				GenericRecord genericRecord = AvroSupport.byteArrayToData(AvroSupport.getSchema(), it.next().message());
				String gameType = AvroSupport.getValue(genericRecord, "gameType", String.class);
				String company = AvroSupport.getValue(genericRecord, "company", String.class);
				System.out.println(company);
			}
		}
		if (consumer != null) {
			consumer.shutdown();
		}
	}

	public static void main(String[] args) {
		String topic = "avro-topic";
		AvroConsumerExample kafkaConsumer = new AvroConsumerExample("localhost:2181", "testConsumerGroup", topic);
		kafkaConsumer.testConsumer();
	}

}
