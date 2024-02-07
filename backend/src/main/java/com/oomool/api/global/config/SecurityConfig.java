package com.oomool.api.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.oomool.api.domain.auth.handler.OAuth2AuthenticationFailureHandler;
import com.oomool.api.domain.auth.handler.OAuth2AuthenticationSuccessHandler;
import com.oomool.api.domain.auth.jwt.JwtAuthorizationFilter;
import com.oomool.api.domain.auth.jwt.JwtExceptionFilter;
import com.oomool.api.domain.auth.service.CustomOAuth2UserService;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity //시큐리티 활성화 -> 기본 스프링 필터 체인에 등록
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
    private final JwtAuthorizationFilter jwtAuthorizationFilter;
    private final JwtExceptionFilter jwtExceptionFilter;

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
            .sessionManagement(
                sessions -> sessions.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS)) // 세션 사용 x, 사용자 지정 토큰 방식 사용(jwt)
            .authorizeHttpRequests(requests -> requests
                .requestMatchers("/login").permitAll()
                .requestMatchers(("/oauth2/token/**")).permitAll() // 토큰 발급을 위한 경로는 모두 허용
                .requestMatchers(("/oauth2/redirect")).permitAll() // redirect url
                .requestMatchers(("/oauth2/access-token")).hasRole("USER")
                // .requestMatchers(("/**")).permitAll()
                .anyRequest().authenticated()) // 나머지 요청은 모두 인증이 필요
            .oauth2Login(oauth2 -> oauth2
                .redirectionEndpoint(endpoint -> endpoint.baseUri("/oauth2/callback/*"))
                .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig.userService(customOAuth2UserService))
                .successHandler(oAuth2AuthenticationSuccessHandler)
                .failureHandler(oAuth2AuthenticationFailureHandler)
            );

        // jwtAuthorization Filter 클래스를 UsernamePasswordAuthenticationFilter 이전에 실행시켜서 jwt 토큰 검증
        http.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
        // filter JWT 토큰 예외 응답 코드를 전달하기 위해 선언한 jwtExceptionFilter를 먼저 실행
        http.addFilterBefore(jwtExceptionFilter, JwtAuthorizationFilter.class);
        return http.build();
    }
}
