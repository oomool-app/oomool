package com.oomool.api.domain.user.auth.service;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.oomool.api.domain.user.auth.user.OAuth2UserInfo;
import com.oomool.api.domain.user.auth.user.OAuth2UserInfoFactory;

@Service
public class OAuth2UserService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // System.out.println("userRequest registrationID :" + userRequest.getClientRegistration().getRegistrationId());

        OAuth2User oAuth2User = super.loadUser(userRequest); // access token을 이용해 서드파티 서버로부터 사용자 정보를 받아온다

        String registrationId = userRequest.getClientRegistration()
            .getRegistrationId();

        String accessToken = userRequest.getAccessToken().getTokenValue();

        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(registrationId,
            accessToken,
            oAuth2User.getAttributes());


        return new OAuth2UserPrincipal(oAuth2UserInfo);
    }
}
