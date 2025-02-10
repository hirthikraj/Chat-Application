package com.app.chat.dto.group;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GroupResponseDTO {
    private String groupName;
    private String groupDescription;
}
