package com.oomool.api.domain.user.auth.user;

import java.util.Map;

import com.oomool.api.domain.user.auth.exception.OAuth2AuthenticationProcessingException;

/**
 * 목적 : 소셜로그인 공급자 명에 따라 다른 로직 처리하기 위해
 */
public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId,
        String accessToken,
        Map<String, Object> attributes) {
        if (registrationId.equals("kakao")) {
            return new KakaoOAuth2UserInfo(accessToken, attributes);
        } else {
            throw new OAuth2AuthenticationProcessingException("Login with " + registrationId + " is not supported");
        }
    }
}

