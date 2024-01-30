package com.oomool.api.domain.user.service;

import org.springframework.stereotype.Service;

import com.oomool.api.domain.user.auth.jwt.RefreshToken;
import com.oomool.api.domain.user.repository.RefreshTokenRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

/**
 * 새로운 토큰을 저장하고, 토큰을 삭제하는 두가지 기능
 */
@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository repository;

    @Transactional
    public void saveTokenInfo(String email, String refreshToken, String accessToken) {
        repository.save(new RefreshToken(email, accessToken, refreshToken));
    }

    @Transactional
    public void removeRefreshToken(String accessToken) {
        RefreshToken token = repository.findByAccessToken(accessToken)
            .orElseThrow(IllegalArgumentException::new);

        repository.delete(token);
    }
}
