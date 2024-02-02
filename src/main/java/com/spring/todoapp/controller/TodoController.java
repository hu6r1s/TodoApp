package com.spring.todoapp.controller;

import com.spring.todoapp.dto.TodoRequestDto;
import com.spring.todoapp.dto.TodoResponseDto;
import com.spring.todoapp.service.TodoService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/todo")
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;

    @PostMapping("/")
    public TodoResponseDto create(@RequestBody TodoRequestDto requestDto, @AuthenticationPrincipal UserDetails userDetails) {
        return todoService.create(requestDto, userDetails);
    }

    @GetMapping("/")
    public List<TodoResponseDto> findAll(@AuthenticationPrincipal UserDetails userDetails) {
        return todoService.findAll(userDetails);
    }

    @GetMapping("/{id}")
    public TodoResponseDto findById(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        return todoService.findById(id, userDetails);
    }

    @PatchMapping("/{id}")
    public TodoResponseDto update(
            @PathVariable Long id,
            @RequestBody TodoRequestDto requestDto,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return todoService.update(id, requestDto, userDetails);
    }

    @DeleteMapping("/{id}")
    public TodoResponseDto delete(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return todoService.delete(id, userDetails);
    }

    @PostMapping("/{id}/completed")
    public TodoResponseDto complete(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return todoService.complete(id, userDetails);
    }
}
