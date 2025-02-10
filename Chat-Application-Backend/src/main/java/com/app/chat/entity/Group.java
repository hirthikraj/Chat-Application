package com.app.chat.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "groups")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Group {
    @Id
    private String groupId;
    private String groupName;
    private String groupDescription;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private String superAdminUserId;
    private List<Message> messages = new ArrayList<>();
    private List<String> members = new ArrayList<>();
    private List<String> admins = new ArrayList<>();
}
