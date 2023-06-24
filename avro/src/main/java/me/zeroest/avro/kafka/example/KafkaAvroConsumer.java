package me.zeroest.avro.kafka.example;

import me.zeroest.avro.User;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

public class KafkaAvroConsumer {
    private static final Logger log = LoggerFactory.getLogger(KafkaAvroConsumer.class);
    private static final String TOPIC_NAME = "avro.user";
    private static final String CONSUMER_GROUP = "test_group";

    public static void main(String[] args) {
        Properties configs = new Properties();

        configs.put(ConsumerConfig.GROUP_ID_CONFIG, CONSUMER_GROUP);
        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
        configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, UserAvroDeserializer.class);

        KafkaConsumer<String, User> consumer = new KafkaConsumer<>(configs);
        consumer.subscribe(List.of(TOPIC_NAME));

        while (true) {
            ConsumerRecords<String, User> records = consumer.poll(Duration.ofSeconds(1L));

            for (ConsumerRecord<String, User> record : records) {
                log.info("record: {}", record);

                User user = record.value();
                log.info("user: {}", user);
            }
        }
    }
}
