package com.oomool.api.domain.user.auth.service;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.oomool.api.domain.auth.entity.SocialLogin;
import com.oomool.api.domain.auth.repository.OAuthRepository;
import com.oomool.api.domain.user.auth.user.OAuth2UserInfo;
import com.oomool.api.domain.user.auth.user.OAuth2UserInfoFactory;
import com.oomool.api.domain.user.dto.Role;
import com.oomool.api.domain.user.entity.User;
import com.oomool.api.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final OAuthRepository oAuthRepository;

    /**
     * 사용자 정보를 반환하고, 반환받은 사용자 정보를 저장하는 메서드
     *
     * 인증된 사용자에 대한 정보는 SecurityContext 객체에 저장되며, 이 객체는 현재 스레드와 연결됩니다.
     */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // System.out.println("userRequest registrationID :" + userRequest.getClientRegistration().getRegistrationId());

        OAuth2User oAuth2User = super.loadUser(userRequest); // access token을 이용해 서드파티 서버로부터 사용자 정보를 받아온다.

        String registrationId = userRequest.getClientRegistration()
            .getRegistrationId();

        String accessToken = userRequest.getAccessToken().getTokenValue();

        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(registrationId,
            accessToken,
            oAuth2User.getAttributes());


        // 사용자 정보가 DB에 이미 저장되어 있는지 확인
        User existingUser = userRepository.findByEmailAndProvider(oAuth2UserInfo.getEmail(), registrationId);

        if (existingUser == null) {
            User newUser = new User();
            newUser.setUsername(oAuth2UserInfo.getNickname());
            newUser.setEmail(oAuth2UserInfo.getEmail());
            newUser.setProvider(registrationId);
            newUser.setRole(Role.USER);

            userRepository.save(newUser);

            // social_login 정보 저장
            SocialLogin newSocial = new SocialLogin();
            newSocial.setUser(newUser);
            newSocial.setProviderId(oAuth2UserInfo.getId());
            newSocial.setProvider(registrationId);

            oAuthRepository.save(newSocial);
        }

        return new OAuth2UserPrincipal(oAuth2UserInfo);
    }
}
