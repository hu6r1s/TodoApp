package com.spring.todoapp.repository;

import com.spring.todoapp.entity.Todo;
import com.spring.todoapp.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findAllByOrderByCreatedAtDesc();
}
