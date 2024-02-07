package com.oomool.api.domain.auth.dto;

import java.util.Map;

import lombok.Getter;

@Getter
public class KakaoOAuth2UserInfo implements OAuth2UserInfo {

    private final Map<String, Object> attributes;
    private final String accessToken;
    private final String id;
    private final String email;
    private final String name;
    private final String firstName;
    private final String lastName;
    private final String nickname;
    private final String profileImageUrl;

    public KakaoOAuth2UserInfo(String accessToken, Map<String, Object> attributes) {
        this.accessToken = accessToken;
        // attributes 맵의 kakao_account 키의 값에 실제 attributes 맵이 할당되어 있음
        Map<String, Object> kakaoAccount = (Map<String, Object>)attributes.get("kakao_account");
        Map<String, Object> kakaoProfile = (Map<String, Object>)kakaoAccount.get("profile");
        this.attributes = kakaoProfile;

        this.id = ((Long)attributes.get("id")).toString();
        this.email = (String)kakaoAccount.get("email");

        this.name = null;
        this.firstName = null;
        this.lastName = null;
        this.nickname = (String)kakaoProfile.get("nickname");

        this.profileImageUrl = null;

        this.attributes.put("id", id);
        this.attributes.put("email", email);
    }

    @Override
    public OAuth2Provider getProvider() {
        return OAuth2Provider.KAKAO;
    }
}
