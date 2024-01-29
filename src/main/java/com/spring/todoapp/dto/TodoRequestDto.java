package com.spring.todoapp.dto;

import lombok.Getter;

@Getter
public class TodoRequestDto {
    private String title;
    private String content;
    private String author;
    private String password;
//    private boolean isCompleted = false;
}