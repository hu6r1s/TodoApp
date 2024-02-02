package com.spring.todoapp.controller;

import com.spring.todoapp.dto.TodoRequestDto;
import com.spring.todoapp.dto.TodoResponseDto;
import com.spring.todoapp.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/todo")
@Tag(name = "Todo", description = "Todo Controller")
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;

    @PostMapping("/")
    @Operation(summary = "할일 생성", description = "할일 카드를 생성한다.")
    public TodoResponseDto create(@RequestBody TodoRequestDto requestDto, @AuthenticationPrincipal UserDetails userDetails) {
        return todoService.create(requestDto, userDetails);
    }

    @GetMapping("/")
    @Operation(summary = "할일 전체 조회", description = "할일 카드를 전체 조회한다.")
    public List<TodoResponseDto> findAll(@AuthenticationPrincipal UserDetails userDetails) {
        return todoService.findAll(userDetails);
    }

    @GetMapping("/{id}")
    @Operation(summary = "할일 조회", description = "할일 카드를 조회한다.")
    public TodoResponseDto findById(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        return todoService.findById(id, userDetails);
    }

    @GetMapping("/search")
    @Operation(summary = "할일 조회", description = "할일 카드를 제목으로 조회한다.")
    public List<TodoResponseDto> findAllByTitle(@RequestParam String title, @AuthenticationPrincipal UserDetails userDetails) {
        return todoService.findAllByTitle(title, userDetails);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "할일 수정", description = "할일 카드를 수정한다.")
    public TodoResponseDto update(
            @PathVariable Long id,
            @RequestBody TodoRequestDto requestDto,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return todoService.update(id, requestDto, userDetails);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "할일 삭제", description = "할일 카드를 삭제한다.")
    public TodoResponseDto delete(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return todoService.delete(id, userDetails);
    }

    @PostMapping("/{id}/completed")
    @Operation(summary = "할일 완료", description = "할일 카드를 완료 처리한다.")
    public TodoResponseDto complete(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return todoService.complete(id, userDetails);
    }
}
