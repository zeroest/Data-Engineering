package me.zeroest.avro.kafka.example;

import me.zeroest.avro.User;
import me.zeroest.avro.kafka.AvroSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class KafkaAvroProducer {
    private static final Logger log = LoggerFactory.getLogger(KafkaAvroProducer.class);
    private static final String TOPIC = "avro.user";

    public static void main(String[] args) {
        Properties configs = new Properties();

        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, AvroSerializer.class);

        KafkaProducer<String, User> producer = new KafkaProducer<>(configs);

        User zero = User.newBuilder()
            .setName("zero")
            .setFavoriteColor("black")
            .setFavoriteNumber(123)
            .build();
        ProducerRecord<String, User> record = new ProducerRecord<>(TOPIC, "key.0", zero);

        producer.send(record);
        log.info("sened: {}", record);
        producer.close();
    }
}
