package com.spring.todoapp.dto;

import com.spring.todoapp.entity.Comment;
import lombok.Getter;

@Getter
public class CommentResponseDto {
    private String comment;
    private String auther;

    public CommentResponseDto(Comment comment) {
        this.comment = comment.getComment();
        this.auther = comment.getUser().getUsername();
    }
}
