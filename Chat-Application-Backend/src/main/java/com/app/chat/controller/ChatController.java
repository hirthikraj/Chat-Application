package com.app.chat.controller;

import com.app.chat.dto.message.MessageRequestDTO;
import com.app.chat.entity.Message;
import com.app.chat.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
@CrossOrigin("http://localhost:5173")
public class ChatController {
    private final ChatService chatService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    ChatController(ChatService chatService,SimpMessagingTemplate simpMessagingTemplate)
    {
        this.chatService = chatService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/sendMessage/{groupId}")
    public Message sendMessage(
            @DestinationVariable String groupId,
            @RequestBody MessageRequestDTO messageRequestDTO
    )
    {
        Message message =  chatService.sendMessage(groupId,messageRequestDTO);
        List<String> userIds = chatService.getUsersInGroup(groupId);
        for(String userId : userIds)
        {
            simpMessagingTemplate.convertAndSend("/topic/user/"+userId,message);
        }
        return message;
    }
}
