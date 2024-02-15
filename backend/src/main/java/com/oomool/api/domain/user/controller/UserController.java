package com.oomool.api.domain.user.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oomool.api.domain.user.dto.MypageDto;
import com.oomool.api.domain.user.dto.UserDto;
import com.oomool.api.domain.user.service.UserService;
import com.oomool.api.global.util.ResponseHandler;

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
    public ResponseEntity<Integer> regist(@RequestBody UserDto userDto) {

        int result = userService.regist(userDto);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @Operation(summary = "회원 로그인 기능(dummy)", description = "이메일을 사용하여 로그인을 시도합니다.")
    @GetMapping("/login")
    public ResponseEntity<UserDto> login(@RequestParam String email) {

        UserDto userDto = userService.searchUserEmail(email);

        if (userDto != null) {
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        }

        return new ResponseEntity<>(userDto, HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "회원 대기방 목록 정보 조회", description = "유저가 참여하고 있는 대기방 목록을 조회합니다.")
    @GetMapping("/{userId}/temps")
    public ResponseEntity<?> getTempRoomList(@PathVariable("userId") Integer userId) {
        List<?> tempRoomList = userService.getTempRoomList(userId);
        return ResponseHandler.generateResponse(HttpStatus.OK, Map.of("temp_room", tempRoomList));
    }

    @Operation(summary = "회원이 참여하고 있는 그룹 목록 조회", description = "유저가 참여하고 있는 그룹방 정보를 조회합니다.")
    @GetMapping("/{userId}/games")
    public ResponseEntity<?> getGameRoomList(@PathVariable("userId") Integer userId) {
        List<?> gameRoomList = userService.getGameRoomList(userId);
        return ResponseHandler.generateResponse(HttpStatus.OK, gameRoomList);
    }

    @Operation(summary = "회원 정보 조회", description = "회원 정보를 조회합니다.")
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserInfo(@PathVariable("userId") Integer userId) {
        MypageDto mypageDto = userService.searchUserInfo(userId);
        return ResponseHandler.generateResponse(HttpStatus.OK, mypageDto);
    }

    @Operation(summary = "회원 닉네임 수정", description = "회원 닉네임을 수정합니다.")
    @PatchMapping("/{userId}")
    public ResponseEntity<?> modifyUserNickname(@PathVariable("userId") Integer userId, @RequestBody String nickname) {
        userService.modifyUsername(userId, nickname);
        return ResponseHandler.generateResponse(HttpStatus.OK, Map.of("nickname", nickname));
    }

    @Operation(summary = "회원 탈퇴", description = "회원이 탈퇴합니다.")
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable("userId") Integer userId) {
        userService.deleteUser(userId);
        return ResponseHandler.generateResponse(HttpStatus.OK, Map.of("message", "회원 탈퇴에 성공했습니다."));
    }
}
