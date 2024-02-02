package com.spring.todoapp.service;

import com.spring.todoapp.common.exception.DuplicatedUserException;
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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
            throw new DuplicatedUserException();
        }

        User user = new User(username, password);
        userRepository.save(user);
        return new UserResponseDto(user);
    }

//    public AuthResponseDto refreshToken(RefreshTokenRequestDto requestDto) {
//        return null;
//    }
}
