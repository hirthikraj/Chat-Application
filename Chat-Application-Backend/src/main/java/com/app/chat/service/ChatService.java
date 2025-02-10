package com.app.chat.service;

import com.app.chat.dto.message.MessageRequestDTO;
import com.app.chat.entity.Group;
import com.app.chat.entity.Message;
import com.app.chat.entity.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatService {
    private final GroupService groupService;
    private final UserService userService;

    ChatService(GroupService groupService,UserService userService)
    {
        this.groupService = groupService;
        this.userService = userService;
    }

    public Message sendMessage(String groupId,MessageRequestDTO messageRequestDTO)
    {
        Message message = Message.builder()
                        .content(messageRequestDTO.getContent())
                        .groupId(groupId)
                        .senderId(messageRequestDTO.getSenderId())
                        .sentTime(LocalDateTime.now())
                        .build();
        groupService.addMessageToGroup(message);
        return message;
    }

    public List<String> getUsersInGroup(String groupId)
    {
        return groupService.getAllUsersInGroup(groupId);
    }
}
