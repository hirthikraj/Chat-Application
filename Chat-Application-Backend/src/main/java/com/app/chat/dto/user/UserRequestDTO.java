package com.app.chat.dto.user;

import com.app.chat.entity.Group;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class UserRequestDTO {
    private String userName;
    private String password;
}
