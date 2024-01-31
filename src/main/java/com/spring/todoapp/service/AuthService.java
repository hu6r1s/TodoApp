package com.spring.todoapp.service;

import com.spring.todoapp.common.util.JwtUtil;
import com.spring.todoapp.dto.AuthResponseDto;
import com.spring.todoapp.dto.RefreshTokenRequestDto;
import com.spring.todoapp.dto.UserRequestDto;
import com.spring.todoapp.dto.UserResponseDto;
import com.spring.todoapp.entity.User;
import com.spring.todoapp.repository.UserRepository;
import java.util.HashMap;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public UserResponseDto signup(UserRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());

        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        User user = new User(username, password);
        userRepository.save(user);
        return new UserResponseDto(user);
    }

    public AuthResponseDto login(UserRequestDto requestDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestDto.getUsername(), requestDto.getPassword()));

        var user = userRepository.findByUsername(requestDto.getUsername()).orElseThrow(() -> new IllegalArgumentException("해당 계정이 존재하지 않습니다."));
        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        var jwt = jwtUtil.generateToken(user);
        var refreshToken = jwtUtil.generateRefreshToken(new HashMap<>(), user);

        return new AuthResponseDto(jwt, refreshToken);
    }
}
