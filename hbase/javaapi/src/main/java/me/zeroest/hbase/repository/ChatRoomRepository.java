package me.zeroest.hbase.repository;

import me.zeroest.hbase.domain.ChatRoom;
import org.apache.hadoop.hbase.client.Connection;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Repository
public class ChatRoomRepository extends AbstractRepository<ChatRoom> {
    public ChatRoomRepository(Connection connection) throws IOException {
        super("chatroom", connection);
    }

    @Override
    public ChatRoom readValue(byte[] raw) throws IOException {
        return objectMapper.readValue(new String(raw, StandardCharsets.UTF_8), ChatRoom.class);
    }
}