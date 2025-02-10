package com.app.chat.exception;

import org.springframework.http.HttpStatus;

public class GroupNotFoundException extends CustomException {
    public GroupNotFoundException(String message, HttpStatus httpStatus) {
        super(message,httpStatus);
    }
}
