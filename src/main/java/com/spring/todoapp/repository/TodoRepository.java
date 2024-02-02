package com.spring.todoapp.repository;

import com.spring.todoapp.entity.Todo;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findAllByIsCompletedFalseOrderByCreatedAtDesc();

    List<Todo> findAllByTitleContaining(String title);
}
