package producer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.commons.io.IOUtils;
import org.apache.zookeeper.Watcher.Event;

import kafka.javaapi.producer.Producer;
import kafka.message.Message;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

public class KafkaProducer {

	private static final SpecificDatumWriter<Event> avroEventWriter = new SpecificDatumWriter<Event>();
	private static final EncoderFactory avroEncoderFactory = EncoderFactory.get();
	private static Producer<String, String> producer = null;

	private static void initializeKafkaProducer() {

		if (producer != null) {
			Properties props = new Properties();
			props.put("metadata.broker.list", "localhost:9092");
			props.put("serializer.class", "kafka.serializer.DefaultEncoder");
			props.put("partitioner.class", "producer.SimplePartitioner");
			props.put("request.required.acks", "1");
			ProducerConfig config = new ProducerConfig(props);
			producer = new Producer<String, String>(config);
		}
	}

	private static void publish(Event event) {
		try {
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			BinaryEncoder binaryEncoder = avroEncoderFactory.binaryEncoder(stream, null);
			avroEventWriter.write(event, binaryEncoder);
			binaryEncoder.flush();
			IOUtils.closeQuietly(stream);

			Message m = new Message(stream.toByteArray());

			producer.send(new KeyedMessage("my-topic", "my-partition-key", m));
		} catch (IOException e) {
			throw new RuntimeException("Avro serialization failure", e);
		}
	}

	private static void sendNormalMessage() {

		KeyedMessage<String, String> data = new KeyedMessage<String, String>("test", "127.0.0.1",
				"Hello From Normal Producer");
		producer.send(data);

	}

	public static void main(String[] args) {

		initializeKafkaProducer();
		sendNormalMessage();
	}

}
