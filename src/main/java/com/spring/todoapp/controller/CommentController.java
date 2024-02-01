package com.spring.todoapp.controller;

import com.spring.todoapp.dto.CommentRequestDto;
import com.spring.todoapp.dto.CommentResponseDto;
import com.spring.todoapp.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/todo/{todoId}")
    public CommentResponseDto create(
            @PathVariable Long todoId,
            @RequestBody CommentRequestDto requestDto,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return commentService.create(todoId, requestDto, userDetails);
    }

    @PatchMapping("/{id}")
    public CommentResponseDto update(
            @PathVariable Long id,
            @RequestBody CommentRequestDto requestDto,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return commentService.update(id, requestDto, userDetails);
    }
}