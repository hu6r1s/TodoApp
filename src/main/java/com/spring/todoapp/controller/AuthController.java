package com.spring.todoapp.controller;

import com.spring.todoapp.common.exception.ExceptionResponse;
import com.spring.todoapp.dto.UserRequestDto;
import com.spring.todoapp.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth", description = "Auth Controller")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    @Operation(summary = "회원가입", description = "유저를 생성한다.")
    public ResponseEntity signup(@RequestBody @Valid UserRequestDto requestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            List<ExceptionResponse> exceptionResponseList = new ArrayList<>();
            for (FieldError fieldError : fieldErrors) {
                ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.BAD_REQUEST, fieldError.getDefaultMessage());
                exceptionResponseList.add(exceptionResponse);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponseList);
        }
        return new ResponseEntity(authService.signup(requestDto), HttpStatus.CREATED);
    }
}
