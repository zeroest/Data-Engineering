package me.zeroest.hbase.repository;

import me.zeroest.hbase.domain.User;
import org.apache.hadoop.hbase.client.Connection;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Repository
public class UserRepository extends AbstractRepository<User> {
    public UserRepository(Connection connection) throws IOException {
        super("user", connection);
    }

    @Override
    public User readValue(byte[] raw) throws IOException {
        return objectMapper.readValue(new String(raw, StandardCharsets.UTF_8), User.class);
    }
}