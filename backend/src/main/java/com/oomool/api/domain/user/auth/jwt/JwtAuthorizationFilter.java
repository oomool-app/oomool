package com.oomool.api.domain.user.auth.jwt;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.oomool.api.domain.user.dto.SecurityUserDto;
import com.oomool.api.domain.user.entity.User;
import com.oomool.api.domain.user.repository.UserRepository;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * 이 클래스는 Spring Security에서 요청을 필터링하여 JWT 토큰을 검증하고, 유효한 토큰이 있는 경우 해당 토큰을 사용하여 사용자를 인증하는 역할
 */
@RequiredArgsConstructor
@Component
@Log4j2
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        // 클라이언트로부터 JWT 액세스 토큰 전달 받는다.
        final String token = resolveToken(request);

        // 토큰이 넘어오지 않았다면 다음 필터로 요청을 전달하는 역할
        if (!StringUtils.hasText(token)) {
            doFilter(request, response, filterChain);
            return;
        }

        // 토큰이 유효한지 검사.
        if (!jwtService.isTokenValid(token)) {
            throw new JwtException("Access Token 만료!");
        }

        // 토큰이 유효하다면

        log.info("호출되는지 체크");

        // AccessToken의 값이 있고, 유효한 경우에 진행한다.
        // AccessToken 내부의 payload에 있는 email로 user를 조회한다. 없다면 예외를 발생시킨다 -> 정상 케이스가 아님
        User findUser = userRepository.findByEmail(jwtService.getEmail(token))
            .orElseThrow(IllegalStateException::new);

        System.out.println("findUser" + findUser.getUsername());

        // SecurityContext에 등록할 User 객체를 만들어준다.
        SecurityUserDto userDto = SecurityUserDto.builder()
            .userId(findUser.getId())
            .email(findUser.getEmail())
            .role(jwtService.getRole(token))
            .nickname(findUser.getUsername())
            .build();

        // SecurityContext에 인증 객체를 등록해준다.
        /**
         * SecurityContextHolder : 현재 실행 중인 애플리케이션의 보안 컨텍스트를 저장하고 액세스하는 데 사용되는 클래스 보안 컨텍스트에는 현재 인증된 사용자 및 해당 사용자의 권한 정보 등이 포함
         */
        Authentication auth = getAuthentication(userDto);
        SecurityContextHolder.getContext().setAuthentication(auth);

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
        return token;
    }

    /**
     * 사용자 인증 정보를 나타내는 객체
     * 인증이 완료되면 SecurityContextHolder에 저장된다.
     */
    public Authentication getAuthentication(SecurityUserDto user) {
        return new UsernamePasswordAuthenticationToken(user, "",
            List.of(new SimpleGrantedAuthority(user.getRole())));
    }
}
