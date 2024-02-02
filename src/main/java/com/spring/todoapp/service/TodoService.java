package com.spring.todoapp.service;

import com.spring.todoapp.common.exception.NotCreatedUserException;
import com.spring.todoapp.dto.TodoRequestDto;
import com.spring.todoapp.dto.TodoResponseDto;
import com.spring.todoapp.entity.Todo;
import com.spring.todoapp.entity.User;
import com.spring.todoapp.repository.TodoRepository;
import com.spring.todoapp.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    @Transactional
    public TodoResponseDto create(TodoRequestDto requestDto, UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(() ->
                new UsernameNotFoundException("회원을 찾을 수 없습니다."));
        Todo todo = new Todo(requestDto, user);
        todoRepository.save(todo);
        return new TodoResponseDto(todo);
    }

    public List<TodoResponseDto> findAll(UserDetails userDetails) {
        List<Todo> todoList = todoRepository.findAllByIsCompletedFalseOrderByCreatedAtDesc();
        List<TodoResponseDto> responseList = new ArrayList<>();
        for (Todo todo : todoList) {
            if (userDetails.getUsername().equals(todo.getUser().getUsername()) || !todo.isHide()) {
                responseList.add(new TodoResponseDto(todo));
            }
        }
        return responseList;
    }

    public TodoResponseDto findById(Long id, UserDetails userDetails) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new NullPointerException("해당하는 ID가 없습니다."));
        if (!userDetails.equals(todo.getUser().getUsername()) || todo.isHide()) {
            throw new IllegalArgumentException("비공개인 카드이거나, 작성자가 일치하지 않습니다.");
        }
        return new TodoResponseDto(todo);
    }

    @Transactional
    public TodoResponseDto update(Long id, TodoRequestDto requestDto, UserDetails userDetails) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new NullPointerException("해당하는 ID가 없습니다."));

        if (!userDetails.getUsername().equals(todo.getUser().getUsername())) {
            throw new NotCreatedUserException();
        }
        todo.update(requestDto);
        return new TodoResponseDto(todo);
    }

    @Transactional
    public TodoResponseDto delete(Long id, UserDetails userDetails) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new NullPointerException("해당하는 ID가 없습니다."));

        if (!userDetails.getUsername().equals(todo.getUser().getUsername())) {
            throw new NotCreatedUserException();
        }

        todoRepository.delete(todo);
        return new TodoResponseDto(todo);
    }

    @Transactional
    public TodoResponseDto complete(Long id, UserDetails userDetails) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new NullPointerException("해당하는 ID가 없습니다."));

        if (!userDetails.getUsername().equals(todo.getUser().getUsername())) {
            throw new NotCreatedUserException();
        }

        todo.complete();
        return new TodoResponseDto(todo);
    }
}
