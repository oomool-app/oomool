package com.oomool.api.domain.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oomool.api.domain.user.dto.UserDto;
import com.oomool.api.domain.user.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "User", description = "유저 관련 API를 명세합니다.")
public class UserController {

    private final UserService userService;

    @Operation(summary = "회원 등록 기능", description = "회원을 등록합니다.")
    @PostMapping
    public ResponseEntity<String> regist(UserDto userDto) {
        String result = "회원이 등록되었습니다";

        userDto.setEmail("pilmo@naver.com");
        userDto.setUserName("pilmo");

        userService.regist(userDto);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
}
