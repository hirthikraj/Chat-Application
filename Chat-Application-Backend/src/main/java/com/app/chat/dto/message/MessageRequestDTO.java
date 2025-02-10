package com.app.chat.dto.message;

import com.app.chat.entity.Group;
import com.app.chat.entity.User;
import lombok.Data;

@Data
public class MessageRequestDTO {
    private String senderId;
    private String groupId;
    private String content;
}
