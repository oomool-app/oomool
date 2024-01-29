package com.oomool.api.domain.user.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth2")
@Log4j2
public class AuthController {


    @GetMapping("/redirect")
    public String createRedirect() {
        return "로그인 성공!";
    }
}

