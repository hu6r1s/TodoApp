package com.spring.todoapp.controller;

import com.spring.todoapp.dto.TodoRequestDto;
import com.spring.todoapp.dto.TodoResponseDto;
import com.spring.todoapp.service.TodoService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;

    @PostMapping("/todo")
    public TodoResponseDto create(@RequestBody TodoRequestDto requestDto) {
        return todoService.create(requestDto);
    }

    @GetMapping("/todos")
    public List<TodoResponseDto> findAll() {
        return todoService.findAll();
    }

    @GetMapping("/todo/{id}")
    public TodoResponseDto findById(@PathVariable Long id) {
        return todoService.findById(id);
    }

    @PatchMapping("/todo/{id}")
    public TodoResponseDto update(@PathVariable Long id, @RequestBody TodoRequestDto requestDto) {
        return todoService.update(id, requestDto);
    }

    @DeleteMapping("/todo/{id}")
    public TodoResponseDto delete(@PathVariable Long id, @RequestBody String password) {
        return todoService.delete(id, password);
    }
}
