package com.oomool.api.domain.user.auth.service;

import java.util.Collections;
import java.util.Map;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.oomool.api.domain.auth.entity.SocialLogin;
import com.oomool.api.domain.auth.repository.OAuthRepository;
import com.oomool.api.domain.user.auth.dto.OAuth2Attribute;
import com.oomool.api.domain.user.auth.dto.OAuth2UserInfo;
import com.oomool.api.domain.user.auth.dto.OAuth2UserInfoFactory;
import com.oomool.api.domain.user.dto.Role;
import com.oomool.api.domain.user.entity.User;
import com.oomool.api.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final OAuthRepository oAuthRepository;

    /**
     * 사용자 정보를 반환하고, 반환받은 사용자 정보를 저장하는 메서드
     *
     * 인증된 사용자에 대한 정보는 SecurityContext 객체에 저장되며, 이 객체는 현재 스레드와 연결됩니다.
     *
     * 로그인 시 DB에서 유저 정보와 권한을 가져온다.
     * */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 기본 OAuth2UserService 객체 생성
        OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();

        // OAuth2UserService를 사용하여 OAuth2User 정보를 가져온다.
        // 카카오 access token을 이용해 서드파티 서버로부터 사용자 정보를 받아온다.(userRequest는 accessToken을 가지고 있다.)
        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);

        log.info("userRe : {}", userRequest);

        // 클라이언트 등록 ID(google, naver, kakao)와 사용자 이름 속성을 가져온다.
        String registrationId = userRequest.getClientRegistration()
            .getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration()
            .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        // OAuth2UserService를 사용하여 가져온 OAuth2User 정보로 OAuth2Attribute 객체를 만든다.
        OAuth2Attribute oAuth2Attribute =
            OAuth2Attribute.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        // OAuth2Attribute의 속성값들을 Map으로 반환 받는다.
        Map<String, Object> userAttribute = oAuth2Attribute.convertToMap();

        // 사용자 email(또는 id) 정보를 가져온다.
        String email = (String)userAttribute.get("email");

        String accessToken = userRequest.getAccessToken().getTokenValue();

        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(registrationId,
            accessToken,
            oAuth2User.getAttributes());

        // 사용자 정보가 DB에 이미 저장되어 있는지 확인
        User existingUser = userRepository.findByEmailAndProvider(email, registrationId);

        // 유저가 존재하지 않을 때 userAttribute의 exist 값을 false로 넣는다.
        if (existingUser == null) {

            userAttribute.put("exist", false);
            // 회원의 권한(회원이 존재하지 않으므로 기본권한인 ROLE_USER를 넣어준다.)

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

            return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                userAttribute, "email");
        }

        // 회원이 존재할 경우, userAttribute의 exist의 값을 true로 넣어준다.
        userAttribute.put("exist", true);
        // 회원의 권한, 회원 속성, 속성 이름을 이용해 DefaultOAuth2User 객체를 생성해 반환한다.

        log.info("getRole : {}", existingUser.getRole().getValue());

        return new DefaultOAuth2User(
            Collections.singleton(new SimpleGrantedAuthority("ROLE_".concat(existingUser.getRole().getValue()))),
            userAttribute, "email");
    }
}
