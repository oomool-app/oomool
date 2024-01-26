package com.oomool.api.domain.auth.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oomool.api.domain.auth.dto.SocialDto;
import com.oomool.api.domain.auth.service.OAuthService;
import com.oomool.api.domain.user.dto.UserSocialDto;
import com.oomool.api.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth")
@Log4j2
public class OAuthController {

    private final OAuthService oAuthService;
    private final UserService userService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 카카오 callback
     * [GET] /oauth/kakao/callback
     */
    @GetMapping("/kakao")
    @CrossOrigin(origins = "https://dev.oomool.site/", methods = {RequestMethod.GET})
    public ResponseEntity<UserSocialDto> kakaoCallback(@RequestParam String code) {
        String accessToken = oAuthService.getKakaoAccessToken(code);

        UserSocialDto userSocialDto = new UserSocialDto();

        try {
            userSocialDto = oAuthService.createKakaoUser(accessToken);
            return new ResponseEntity<>(userSocialDto, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(userSocialDto, HttpStatus.ACCEPTED);
    }

    @PostMapping
    public ResponseEntity<Integer> kakaoRegist(@RequestBody SocialDto socialDto) {
        int result = oAuthService.socialKakaoRegist(socialDto);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
}
