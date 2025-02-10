package com.app.chat.dto.user;

import com.app.chat.entity.Group;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
public class UserResponseDTO {
    private String userName;
    private int userStatus;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private LocalDateTime lastActiveTime;
    private List<Group> userVsGroups;
}
