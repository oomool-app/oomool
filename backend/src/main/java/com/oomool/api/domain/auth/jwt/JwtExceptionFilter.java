package com.oomool.api.domain.auth.jwt;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * filter에서 JWT 토큰 예외 발생 시 클라이언트에 401 응답 코드를 전달
 */
@Component
@RequiredArgsConstructor
public class JwtExceptionFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws
        ServletException, IOException {
        try {
            logger.info("jwt 검증 통과");
            filterChain.doFilter(request, response);
        } catch (JwtException e) {
            logger.info("jwtExceptionFilter error");
            response.setStatus(401);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
            objectMapper.writeValue(response.getWriter(), StatusResponseDto.addStatus(401));
        }
    }
}
