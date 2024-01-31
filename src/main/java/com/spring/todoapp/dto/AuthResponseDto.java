package com.spring.todoapp.dto;

import lombok.Getter;

@Getter
public class AuthResponseDto {
    private String token;
    private String refreshToken;

    public AuthResponseDto(String jwt, String refreshToken) {
        this.token = jwt;
        this.refreshToken = refreshToken;
    }
}