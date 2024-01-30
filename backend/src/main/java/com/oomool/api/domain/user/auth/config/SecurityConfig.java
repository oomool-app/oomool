package com.oomool.api.domain.user.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.oomool.api.domain.user.auth.handler.OAuth2AuthenticationFailureHandler;
import com.oomool.api.domain.user.auth.handler.OAuth2AuthenticationSuccessHandler;
import com.oomool.api.domain.user.auth.jwt.JwtAuthorizationFilter;
import com.oomool.api.domain.user.auth.service.OAuth2UserService;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity //시큐리티 활성화 -> 기본 스프링 필터 체인에 등록
public class SecurityConfig {

    private final OAuth2UserService oAuth2UserService;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
    private final JwtAuthorizationFilter jwtAuthorizationFilter;

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(requests -> requests
                .requestMatchers(("/oauth2/access-token")).hasRole("USER")
                .requestMatchers(("/oauth2/redirect")).permitAll()
                .anyRequest().authenticated()) // 나머지 요청은 모두 인증이 필요
            .sessionManagement(
                sessions -> sessions.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS)) // 세션 사용 x, 사용자 지정 토큰 방식 사용(jwt)
            .oauth2Login(oauth2 -> oauth2
                .redirectionEndpoint(endpoint -> endpoint.baseUri("/oauth2/callback/*"))
                .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig.userService(oAuth2UserService))
                .successHandler(oAuth2AuthenticationSuccessHandler)
                .failureHandler(oAuth2AuthenticationFailureHandler)
            );

        // jwtAuthorization Filter 클래스를 UsernamePasswordAuthenticationFilter 이전에 실행시켜서 jwt 토큰 검증
        http.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
