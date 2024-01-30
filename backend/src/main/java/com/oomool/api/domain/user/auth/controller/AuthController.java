package com.oomool.api.domain.user.auth.controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oomool.api.domain.user.auth.jwt.JwtService;
import com.oomool.api.domain.user.auth.jwt.RefreshToken;
import com.oomool.api.domain.user.auth.jwt.StatusResponseDto;
import com.oomool.api.domain.user.dto.response.TokenResponseStatus;
import com.oomool.api.domain.user.repository.RefreshTokenRepository;
import com.oomool.api.domain.user.service.RefreshTokenService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth2")
@Log4j2
public class AuthController {

    private final RefreshTokenRepository tokenRepository;
    private final RefreshTokenService tokenService;
    private final JwtService jwtService;

    /**
     * 로그아웃 시 리프레시 토큰 삭제
     */
    @PostMapping("token/logout")
    public ResponseEntity<StatusResponseDto> logout(@RequestHeader("Authorization") final String accessToken) {

        // 엑세스 토큰으로 현재 Redis 정보 삭제
        tokenService.removeRefreshToken(accessToken);
        return ResponseEntity.ok(StatusResponseDto.addStatus(200));
    }

    /**
     * 토큰 재발급 API
     */
    @PostMapping("/token/refresh")
    public ResponseEntity<TokenResponseStatus> refresh(@RequestHeader("Authorization") final String accessToken) {

        // 액세스 토큰으로 Refresh 토큰 객체를 조회
        Optional<RefreshToken> refreshToken = tokenRepository.findByAccessToken(accessToken);
        log.info("refreshToken : " + refreshToken);

        // RefreshToken이 존재하고 유효하다면 실행
        if (refreshToken.isPresent() && jwtService.isTokenValid(refreshToken.get().getRefreshToken())) {
            // RefreshToken 객체를 꺼내온다.
            RefreshToken resultToken = refreshToken.get();
            // 권한과 아이디를 추출해 새로운 액세스토큰을 만든다.
            String newAccessToken = jwtService.generateToken(resultToken.getId(),
                jwtService.getRole(resultToken.getRefreshToken()));
            // 액세스 토큰의 값을 수정해준다.
            resultToken.updateAccessToken(newAccessToken);
            log.info("newAccessToken : " + newAccessToken);
            tokenRepository.save(resultToken);
            // 새로운 액세스 토큰을 반환해준다.
            return ResponseEntity.ok(TokenResponseStatus.addStatus(200, newAccessToken));
        }

        return ResponseEntity.badRequest().body(TokenResponseStatus.addStatus(400, null));

    }

    // 시큐리티 인증 / 인가 테스트 하기 위한 API
    @GetMapping("/access-token")
    public String tokenTest(@RequestHeader("Authorization") final String accessToken) {
        return accessToken;
    }

    // 로그인 성공 시 redirect api
    @GetMapping("/redirect")
    public String createRedirect() {
        return "로그인 성공!";
    }
}

