package com.oomool.api.domain.user.auth.handler;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.oomool.api.domain.user.auth.jwt.JwtService;
import com.oomool.api.domain.user.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * OAuth2 인증 성공 시 처리를 담당하는 핸들러
 */
@Log4j2
@RequiredArgsConstructor
@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    /**
     * 인증이 성공했을 때 호출되는 메서드로, 실제로 어떤 동작을 할지를 결정
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException {

        String email = authentication.getName();
        String role = authentication.getAuthorities().toString();

        String url = makeRedirectUrl(jwtService.generateToken(email, role));
        String token = jwtService.generateToken(email, role); // 액세스 토큰
        String refreshToken = jwtService.generateRefreshToken(email, role); // 리프레시 토큰


        // 응답 헤더에 액세스 토큰 반환
        addTokenToHeader(response, token);

        if (response.isCommitted()) {
            logger.debug("응답이 이미 커밋된 상태입니다. 리다이렉트를 수행할 수 없습니다");
            return;
        }

        // 지정한 url로 redirect
        getRedirectStrategy().sendRedirect(request, response, url);
    }

    // 응답 헤더에 'Authorization' 이름으로 액세스 토큰 전달
    private void addTokenToHeader(HttpServletResponse response, String token) {
        response.addHeader("Authorization", "Bearer " + token);
    }

    /**
     * 리디렉션 대상 URL을 결정하는 메서드
     */
    private String makeRedirectUrl(String token) {
        return UriComponentsBuilder.fromUriString("http://localhost:8080/oauth2/redirect")
            .build()
            .toUriString();
    }
}
