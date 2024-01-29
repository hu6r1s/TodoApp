package com.spring.todoapp.service;

import com.spring.todoapp.dto.TodoRequestDto;
import com.spring.todoapp.dto.TodoResponseDto;
import com.spring.todoapp.entity.Todo;
import com.spring.todoapp.repository.TodoRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;

    @Transactional
    public TodoResponseDto create(TodoRequestDto requestDto) {
        Todo todo = new Todo(requestDto);
        todoRepository.save(todo);
        return new TodoResponseDto(todo);
    }

    public List<TodoResponseDto> findAll() {
        return todoRepository.findAllByOrderByCreatedAtDesc().stream().map(TodoResponseDto::new).toList();
    }

    public TodoResponseDto findById(Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new NullPointerException("해당하는 ID가 없습니다."));
        return new TodoResponseDto(todo);
    }

    @Transactional
    public TodoResponseDto update(Long id, TodoRequestDto requestDto) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new NullPointerException("해당하는 ID가 없습니다."));

        if (!todo.getPassword().equals(requestDto.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        todo.update(requestDto);
        return new TodoResponseDto(todo);
    }

    @Transactional
    public TodoResponseDto delete(Long id, String password) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new NullPointerException("해당하는 ID가 없습니다."));
        if (!todo.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        todoRepository.delete(todo);

        return new TodoResponseDto(todo);
    }
}
