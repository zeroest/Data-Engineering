package me.zeroest.hbase.controller;

import lombok.RequiredArgsConstructor;
import me.zeroest.hbase.controller.request.GetMessagesLatestRequest;
import me.zeroest.hbase.controller.request.GetMessagesRequest;
import me.zeroest.hbase.controller.request.SendMessageRequest;
import me.zeroest.hbase.domain.ChatMessage;
import me.zeroest.hbase.domain.ChatRoom;
import me.zeroest.hbase.domain.User;
import me.zeroest.hbase.service.ChatService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/room")
    public ChatRoom createChat(@RequestBody List<User> users) throws IOException {
        return chatService.createChatRoom(users);
    }
/*
hbase:012:0> scan 'chatroom'
ROW                                           COLUMN+CELL
 wjcwPLJNYo                                   column=cf:q, timestamp=2023-09-10T16:11:35.964, value={"id":"wjcwPLJNYo","participantIds":["kJqoCrjHzL","iiFRMHMSOn"]}
1 row(s)
Took 0.0356 seconds
*/

    @PostMapping("/message")
    public ChatMessage sendMessage(@RequestBody SendMessageRequest request) throws IOException {
        return chatService.sendMessage(request);
    }

    /*
    hbase:013:0> scan 'chatmessage'
    ROW                                           COLUMN+CELL
     wjcwPLJNYo\x7F\xFF\xFEu\x80\x1F\xB9)_\xD4I\x column=cf:q, timestamp=2023-09-10T16:15:19.579, value={"chatRoomId":"wjcwPLJNYo","senderUserId":"kJqoCrjHzL","content":"\xEC\x95\x88
     A8\x8C\x1EI\x07\xF9\xCC_\x92rv\x94v\x1A/Q!\x \xEB\x85\x95\xED\x95\x98\xEC\x84\xB8\xEC\x9A\xA5","timestamp":1694362519254}
     DCJ\xF2\xC2\xC2\x82\x83I\xD4h\xB7\x8C
    1 row(s)
    Took 0.0423 seconds
    */
    @GetMapping("/message")
    public List<ChatMessage> getMessages(@RequestBody GetMessagesRequest request) throws IOException {
        return chatService.getMessages(request);
    }
/*
{
  "start": {
    "chatRoomId": "wjcwPLJNYo",
    "senderUserId": "kJqoCrjHzL",
    "content": "안녕하4",
    "timestamp": 1694363011010
  },
  "limit": 1
}

[
  {
    "chatRoomId": "wjcwPLJNYo",
    "senderUserId": "kJqoCrjHzL",
    "content": "안녕하4",
    "timestamp": 1694363011010
  },
  {
    "chatRoomId": "wjcwPLJNYo",
    "senderUserId": "kJqoCrjHzL",
    "content": "안녕하세3",
    "timestamp": 1694362913178
  }
]
*/

    @GetMapping("/message/latest")
    public List<ChatMessage> getMessagesLatest(@RequestBody GetMessagesLatestRequest request) throws IOException {
        return chatService.getMessagesFromLatest(request.getChatRoomId(), request.getLimit());
    }
/*
{
  "chatRoomId": "wjcwPLJNYo",
  "limit": 10
}

[
  {
    "chatRoomId": "wjcwPLJNYo",
    "senderUserId": "kJqoCrjHzL",
    "content": "안녕하4",
    "timestamp": 1694363011010
  },
  {
    "chatRoomId": "wjcwPLJNYo",
    "senderUserId": "kJqoCrjHzL",
    "content": "안녕하세3",
    "timestamp": 1694362913178
  },
  {
    "chatRoomId": "wjcwPLJNYo",
    "senderUserId": "kJqoCrjHzL",
    "content": "안녕하세욥2",
    "timestamp": 1694362905859
  },
  {
    "chatRoomId": "wjcwPLJNYo",
    "senderUserId": "kJqoCrjHzL",
    "content": "안녕하세욥",
    "timestamp": 1694362519254
  }
]
*/

}
