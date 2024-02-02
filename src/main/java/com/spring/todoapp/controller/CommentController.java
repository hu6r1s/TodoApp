package com.spring.todoapp.controller;

import com.spring.todoapp.dto.CommentRequestDto;
import com.spring.todoapp.dto.CommentResponseDto;
import com.spring.todoapp.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Comment", description = "Comment Controller")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/todo/{todoId}")
    @Operation(summary = "댓글 생성", description = "댓글을 작성한다.")
    public CommentResponseDto create(
            @PathVariable Long todoId,
            @RequestBody CommentRequestDto requestDto,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return commentService.create(todoId, requestDto, userDetails);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "댓글 수정", description = "댓글을 수정한다.")
    public CommentResponseDto update(
            @PathVariable Long id,
            @RequestBody CommentRequestDto requestDto,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return commentService.update(id, requestDto, userDetails);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "댓글 삭제", description = "댓글을 삭제한다.")
    public CommentResponseDto delete(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return commentService.delete(id, userDetails);
    }
}