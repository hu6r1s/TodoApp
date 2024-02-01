package com.spring.todoapp.dto;

import com.spring.todoapp.entity.User;
import lombok.Getter;

@Getter
public class TodoRequestDto {
    private String title;
    private String content;
}