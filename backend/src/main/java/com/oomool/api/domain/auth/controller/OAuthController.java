package com.oomool.api.domain.auth.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.oomool.api.domain.auth.service.OAuthService;
import com.oomool.api.domain.user.dto.UserSocialDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth")
@Log4j2
public class OAuthController {

    private final OAuthService oAuthService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 카카오 callback
     * [GET] /oauth/kakao/callback
     */
    @GetMapping("/kakao")
    @ResponseBody
    public void kakaoCallback(@RequestParam String code) {
        String accessToken = oAuthService.getKakaoAccessToken(code);
        UserSocialDto userSocialDto = new UserSocialDto();

        try {
            userSocialDto = oAuthService.createKakaoUser(accessToken);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
