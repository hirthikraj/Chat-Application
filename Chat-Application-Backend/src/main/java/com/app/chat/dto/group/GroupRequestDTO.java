package com.app.chat.dto.group;

import com.app.chat.entity.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class GroupRequestDTO {
    private String groupName;
    private String groupDescription;
    private String userId;
}
