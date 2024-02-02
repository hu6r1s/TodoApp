package com.spring.todoapp.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AuthResponseDto {
    private String message;
    private HttpStatus status;
    private int statusCode;

    public AuthResponseDto(String message, HttpStatus status, int statusCode) {
        this.message = message;
        this.status = status;
        this.statusCode = statusCode;
    }
}