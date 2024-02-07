package com.oomool.api.domain.user.service;

import org.springframework.stereotype.Service;

import com.oomool.api.domain.auth.jwt.RefreshToken;
import com.oomool.api.domain.user.repository.RefreshTokenRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * 새로운 토큰을 저장하고, 토큰을 삭제하는 두가지 기능
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository repository;

    @Transactional
    public void saveTokenInfo(String email, String refreshToken, String accessToken) {
        RefreshToken saveToken = RefreshToken
            .builder()
            .id(email)
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
        repository.save(saveToken);
    }

    @Transactional
    public void removeRefreshToken(String accessToken) {
        RefreshToken token = repository.findByAccessToken(accessToken)
            .orElseThrow(IllegalArgumentException::new);
        repository.delete(token);
    }
}
