package com.oomool.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping("login")
    public String login() {
        return "로그인 화면";
    }
}
