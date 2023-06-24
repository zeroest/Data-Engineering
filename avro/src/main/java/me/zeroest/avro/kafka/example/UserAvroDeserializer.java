package me.zeroest.avro.kafka.example;

import me.zeroest.avro.User;
import me.zeroest.avro.kafka.AvroDeserializer;

public class UserAvroDeserializer extends AvroDeserializer<User> {
    public UserAvroDeserializer() {
        super(User.class);
    }
}
