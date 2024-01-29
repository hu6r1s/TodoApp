package com.spring.todoapp.repository;

import com.spring.todoapp.dto.TodoResponseDto;
import com.spring.todoapp.entity.Todo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<TodoResponseDto> findAllByOrderByCreatedAtDesc();
}
