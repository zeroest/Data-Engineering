package me.zeroest.hbase.service;

import lombok.RequiredArgsConstructor;
import me.zeroest.hbase.controller.request.GetMessagesRequest;
import me.zeroest.hbase.controller.request.SendMessageRequest;
import me.zeroest.hbase.domain.ChatMessage;
import me.zeroest.hbase.domain.ChatRoom;
import me.zeroest.hbase.domain.User;
import me.zeroest.hbase.repository.ChatMessageRepository;
import me.zeroest.hbase.repository.ChatRoomRepository;
import me.zeroest.hbase.repository.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;

    public User createRandomUser() throws IOException {
        User user = new User(
            RandomStringUtils.randomAlphabetic(10),
            RandomStringUtils.randomAlphabetic(5),
            RandomStringUtils.randomAlphabetic(5)
        );
        userRepository.put(user);
        return user;
    }

    public ChatRoom createChatRoom(List<User> users) throws IOException {
        Set<String> participantIds = users.stream()
            .map(User::getId)
            .collect(Collectors.toSet());
        ChatRoom chatRoom = new ChatRoom(
            RandomStringUtils.randomAlphabetic(10),
            participantIds
        );

        chatRoomRepository.put(chatRoom);
        return chatRoom;
    }

    public ChatMessage sendMessage(SendMessageRequest request) throws IOException {
        ChatMessage chatMessage = new ChatMessage(
            request.getChatRoomId(),
            request.getSenderUserId(),
            request.getContent(),
            System.currentTimeMillis()
        );

        chatMessageRepository.put(chatMessage);
        return chatMessage;
    }

    public List<ChatMessage> getMessages(GetMessagesRequest request) throws IOException {
        List<ChatMessage> chatMessages = chatMessageRepository.scan(
            request.getStart(),
            request.getLimit() + 1,
            false
        );

        return chatMessages;
    }

    public List<ChatMessage> getMessagesFromLatest(String chatRoomId, int limit) throws IOException {
        List<ChatMessage> chatMessages = chatMessageRepository.scanPrefix(
            Bytes.toBytes(chatRoomId),
            limit + 1
        );
        return chatMessages;
    }

}
