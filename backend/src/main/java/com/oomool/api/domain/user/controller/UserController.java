package com.oomool.api.domain.user.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oomool.api.domain.user.dto.UserDto;
import com.oomool.api.domain.user.service.UserService;
import com.oomool.api.global.dto.ApiResponse;

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
    public ApiResponse<?> regist(@RequestBody UserDto userDto) {
        int result = userService.regist(userDto);
        return ApiResponse.success(result);
    }

    @Operation(summary = "회원 로그인 기능(dummy)", description = "이메일을 사용하여 로그인을 시도합니다.")
    @GetMapping("/login")
    public ApiResponse<?> login(@RequestParam String email) {
        UserDto userDto = userService.searchUserEmail(email);
        return ApiResponse.success(userDto);
    }

    @Operation(summary = "회원 대기방 목록 정보 조회", description = "유저가 참여하고 있는 대기방 목록을 조회합니다.")
    @GetMapping("/{userId}/temps")
    public ApiResponse<?> getTempRoomList(@PathVariable("userId") Integer userId) {
        List<?> tempRoomList = userService.getTempRoomList(userId);
        return ApiResponse.success(Map.of("temp_room", tempRoomList));
    }

    @Operation(summary = "회원이 참여하고 있는 그룹 목록 조회", description = "유저가 참여하고 있는 그룹방 정보를 조회합니다.")
    @GetMapping("/{userId}/games")
    public ApiResponse<?> getGameRoomList(@PathVariable("userId") Integer userId) {
        List<?> gameRoomList = userService.getGameRoomList(userId);
        return ApiResponse.success(gameRoomList);
    }

}
