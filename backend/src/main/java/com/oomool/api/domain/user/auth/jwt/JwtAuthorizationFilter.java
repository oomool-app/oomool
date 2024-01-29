package com.oomool.api.domain.user.auth.jwt;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * 이 클래스는 Spring Security에서 요청을 필터링하여 JWT 토큰을 검증하고, 유효한 토큰이 있는 경우 해당 토큰을 사용하여 사용자를 인증하는 역할
 */
@RequiredArgsConstructor
@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        final String token = resolveToken(request);

        if (StringUtils.hasText(token) && jwtService.isTokenValid(token)) {
            Authentication authentication = jwtService.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication); // 현재 인증된 주체를 변경하거나 인증 정보를 제거합니다.
        }

        filterChain.doFilter(request, response);
    }

    /**
     *  "Authorization" 헤더를 확인하고, 해당 헤더가 존재하고 "Bearer "로 시작하는 경우에 토큰을 반환합니다.
     */
    private String resolveToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return null;
    }
}
