package com.oomool.api.domain.auth.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 목적 : 다른 소셜 로그인 기능을 추가할 수도 있기 때문에 확장성을 위해
 */
@Getter
@RequiredArgsConstructor
public enum OAuth2Provider {

    KAKAO("kakao");

    private final String registrationId;
}
