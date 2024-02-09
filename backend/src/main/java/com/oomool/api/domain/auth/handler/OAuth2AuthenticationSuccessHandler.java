package com.oomool.api.domain.auth.handler;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.oomool.api.domain.auth.jwt.JwtService;
import com.oomool.api.domain.user.entity.User;
import com.oomool.api.domain.user.repository.UserRepository;
import com.oomool.api.domain.user.service.RefreshTokenService;

import jakarta.persistence.EntityNotFoundException;
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
    private final RefreshTokenService refreshTokenService;
    private final UserRepository userRepository;

    @Value("${frontend.redirect.uri}")
    private String redirectUrl;

    /**
     * 인증이 성공했을 때 호출되는 메서드로, 실제로 어떤 동작을 할지를 결정
     * JWT, refresj Token을 생성하고 액세스 토큰은 response.Header에 담아서 보내고,
     * refreshToken은 redis에 저장.
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException {

        String email = authentication.getName();
        String role = authentication.getAuthorities().toString();

        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new EntityNotFoundException("해당 유저를 찾을 수 없습니다"));

        int userId = user.getId();
        String token = jwtService.generateToken(String.valueOf(userId), email, role); // 액세스 토큰
        String url = makeRedirectUrl(token, user);
        String refreshToken = jwtService.generateRefreshToken(email, role); // 리프레시 토큰 생성

        // redis에 저장
        refreshTokenService.saveTokenInfo(String.valueOf(userId), email, refreshToken, "Bearer " + token);

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
    private String makeRedirectUrl(String token, User user) {

        return UriComponentsBuilder.fromUriString(redirectUrl + "/oauth/kakao")
            .queryParam("token", token)
            .queryParam("userId", user.getId())
            .queryParam("email", user.getEmail())
            .queryParam("username", user.getUsername())
            .build()
            .encode()
            .toUriString();
    }
}
