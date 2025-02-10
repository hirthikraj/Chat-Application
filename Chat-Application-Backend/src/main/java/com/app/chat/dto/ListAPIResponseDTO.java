package com.app.chat.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class ListAPIResponseDTO {
    List<?> pageData;
    int size;
}
