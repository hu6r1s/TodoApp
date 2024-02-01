package com.spring.todoapp.dto;

import com.spring.todoapp.entity.User;
import lombok.Getter;

@Getter
public class UserResponseDto {
    private String username;

    public UserResponseDto(User user) {
        this.username = user.getUsername();
    }
}