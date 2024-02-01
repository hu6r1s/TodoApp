package com.spring.todoapp.dto;

import com.spring.todoapp.entity.Todo;
import com.spring.todoapp.entity.User;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class TodoResponseDto {
    private Long id;
    private String title;
    private String content;
    private String author;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private boolean isCompleted;


    public TodoResponseDto(Todo todo) {
        this.id = todo.getId();
        this.title = todo.getTitle();
        this.content = todo.getContent();
        this.author = todo.getUser().getUsername();
        this.createdAt = todo.getCreatedAt();
        this.modifiedAt = todo.getModifiedAt();
        this.isCompleted = todo.isCompleted();
    }
}