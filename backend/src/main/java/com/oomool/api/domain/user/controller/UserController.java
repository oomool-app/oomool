package com.oomool.api.domain.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oomool.api.domain.user.dto.UserDto;
import com.oomool.api.domain.user.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Api(tags = {"유저 컨트롤러 API V1"})
public class UserController {

    private final UserService userService;

    // @GetMapping
    // public ResponseEntity<User> findUser(){
    //
    // }

    @ApiOperation(value = "명소 검색 기능", notes = "명소 검색 기능")
    @PostMapping
    public ResponseEntity regist(UserDto userDto) {
        String result = "회원이 등록되었습니다";

        userDto.setEmail("pilmo@naver.com");
        userDto.setUserName("pilmo");

        userService.regist(userDto);

        return new ResponseEntity<String>(result, HttpStatus.CREATED);
    }
}
