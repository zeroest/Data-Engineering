package me.zeroest.hbase.repository;

import me.zeroest.hbase.domain.ChatMessage;
import org.apache.hadoop.hbase.client.Connection;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Repository
public class ChatMessageRepository extends AbstractRepository<ChatMessage> {
    public ChatMessageRepository(Connection connection) throws IOException {
        super("chatmessage", connection);
    }

    @Override
    public ChatMessage readValue(byte[] raw) throws IOException {
        return objectMapper.readValue(new String(raw, StandardCharsets.UTF_8), ChatMessage.class);
    }
}