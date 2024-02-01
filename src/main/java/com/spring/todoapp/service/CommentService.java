package com.spring.todoapp.service;

import com.spring.todoapp.dto.CommentRequestDto;
import com.spring.todoapp.dto.CommentResponseDto;
import com.spring.todoapp.entity.Comment;
import com.spring.todoapp.entity.Todo;
import com.spring.todoapp.entity.User;
import com.spring.todoapp.repository.CommentRepository;
import com.spring.todoapp.repository.TodoRepository;
import com.spring.todoapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    public CommentResponseDto create(Long todoId, CommentRequestDto requestDto, UserDetails userDetails) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 ID입니다."));

        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(() ->
                new UsernameNotFoundException("해당 유저가 존재하지 않습니다."));

        Comment comment = new Comment(requestDto, user, todo);
        commentRepository.save(comment);

        return new CommentResponseDto(comment);
    }
}
