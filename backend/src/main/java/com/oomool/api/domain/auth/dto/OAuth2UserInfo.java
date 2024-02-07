package com.oomool.api.domain.auth.dto;

import java.util.Map;

public interface OAuth2UserInfo {

    OAuth2Provider getProvider();

    String getAccessToken();

    Map<String, Object> getAttributes();

    String getId();

    String getEmail();

    String getName();

    // FirstName, LastName은 다른 소셜 로그인에서 존재하는 반환 값이기 떄문에 확장성을 위해 일단 생성
    String getFirstName();

    String getLastName();

    String getNickname();

    // 프로필 이미지 url로 확장성을 위해 일단 생성.
    String getProfileImageUrl();
}
