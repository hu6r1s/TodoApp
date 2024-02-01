package com.spring.todoapp.controller;

import com.spring.todoapp.dto.RefreshTokenRequestDto;
import com.spring.todoapp.dto.UserRequestDto;
import com.spring.todoapp.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody UserRequestDto requestDto) {
        return new ResponseEntity(authService.signup(requestDto), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserRequestDto requestDto, HttpServletResponse servletResponse) {
        var response = authService.login(requestDto);

        servletResponse.addHeader("Authorization", "Bearer " + response.getToken());
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity refresh(@RequestBody RefreshTokenRequestDto requestDto) {
        return new ResponseEntity(authService.refreshToken(requestDto), HttpStatus.OK);
    }
}
