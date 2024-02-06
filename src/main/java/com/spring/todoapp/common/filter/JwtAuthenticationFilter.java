package com.spring.todoapp.common.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.todoapp.common.util.JwtUtil;
import com.spring.todoapp.common.util.UserDetailsImpl;
import com.spring.todoapp.dto.AuthResponseDto;
import com.spring.todoapp.dto.UserRequestDto;
import com.spring.todoapp.entity.RefreshToken;
import com.spring.todoapp.entity.User;
import com.spring.todoapp.repository.RefreshTokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;
    ObjectMapper objectMapper = new ObjectMapper();
    private final RefreshTokenRepository refreshTokenRepository;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, RefreshTokenRepository refreshTokenRepository) {
        this.jwtUtil = jwtUtil;
        this.refreshTokenRepository = refreshTokenRepository;
        setFilterProcessesUrl("/api/v1/auth/login");
    }



    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UserRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), UserRequestDto.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.getUsername(),
                            requestDto.getPassword(),
                            null
                    )
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException {
        User user = ((UserDetailsImpl) authResult.getPrincipal()).getUser();

        String accessToken = jwtUtil.createAccessToken(user.getUsername());
        String refreshToken = jwtUtil.createRefreshToken(user.getUsername()).substring(7);
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, accessToken);
        refreshTokenRepository.save(new RefreshToken(refreshToken, user));

        response.setStatus(HttpServletResponse.SC_OK);

        String jsonResponse = objectMapper.writeValueAsString(new AuthResponseDto("로그인에 성공하였습니다.", HttpStatus.OK, response.getStatus()));

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed)
            throws IOException {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

        String jsonResponse = objectMapper.writeValueAsString(new AuthResponseDto("로그인에 실패하였습니다.", HttpStatus.BAD_REQUEST, response.getStatus()));

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse);
    }

}