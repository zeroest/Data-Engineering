package me.zeroest.hbase.controller;

import lombok.RequiredArgsConstructor;
import me.zeroest.hbase.domain.User;
import me.zeroest.hbase.service.ChatService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final ChatService chatService;

    @PostMapping
    public User addUser() throws IOException {
        return chatService.createRandomUser();
    }
/*
hbase:011:0> scan 'user'
ROW                                           COLUMN+CELL
 iiFRMHMSOn                                   column=cf:q, timestamp=2023-09-10T16:07:03.931, value={"id":"iiFRMHMSOn","name":"kYwdn","nickname":"qHPLF"}
 kJqoCrjHzL                                   column=cf:q, timestamp=2023-09-10T16:05:20.560, value={"id":"kJqoCrjHzL","name":"GTDfT","nickname":"NGcqn"}
2 row(s)
Took 0.0184 seconds
*/

}
