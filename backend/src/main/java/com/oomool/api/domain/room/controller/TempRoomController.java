package com.oomool.api.domain.room.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oomool.api.domain.player.dto.PlayerDto;
import com.oomool.api.domain.room.dto.SettingOptionDto;
import com.oomool.api.domain.room.dto.TempRoomBanRequestDto;
import com.oomool.api.domain.room.dto.TempRoomRequestDto;
import com.oomool.api.domain.room.service.TempRoomService;
import com.oomool.api.domain.user.service.UserService;
import com.oomool.api.global.dto.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/temp")
@RequiredArgsConstructor
@Tag(name = "대기방", description = "대기방을 생성하는 API를 명세합니다.")
public class TempRoomController {

    private final TempRoomService tempRoomService;
    private final UserService userService;

    @Operation(summary = "대기방 생성 기능", description = "대기방을 생성합니다.")
    @PostMapping
    public ApiResponse<?> createTempRoom(@RequestBody TempRoomRequestDto request) {

        int masterId = userService.getUserIdByEmail(request.getMaster().getEmail());

        return ApiResponse.success(tempRoomService.createTempRoom(request.getSetting(), masterId));
    }

    @Operation(summary = "대기방 조회 기능")
    @GetMapping("/{inviteCode}")
    public ApiResponse<?> getTempRoom(@PathVariable("inviteCode") String inviteCode) {

        return ApiResponse.success(tempRoomService.getTempRoomDetail(inviteCode));
    }

    @Operation(summary = "대기방 입장")
    @PostMapping("/{inviteCode}/players")
    public ApiResponse<?> joinTempRoom(@PathVariable("inviteCode") String inviteCode,
        @RequestBody PlayerDto player) {

        return ApiResponse.success(tempRoomService.joinTempRoom(inviteCode, player));
    }

    @Operation(summary = "대기방 삭제", description = "방장은 대기방을 삭제할 수 있다.")
    @DeleteMapping("/{inviteCode}")
    public ApiResponse<?> deleteTempRoom(@PathVariable("inviteCode") String inviteCode,
        @RequestParam("id") int userId) {

        return ApiResponse.success(tempRoomService.deleteTempRoom(inviteCode, userId));
    }

    @Operation(summary = "플레이어 퇴장 ", description = "플레이어는 대기방을 퇴장할 수 있다.")
    @DeleteMapping("/{inviteCode}/{userId}")
    public ApiResponse<?> exitTempRoom(@PathVariable("inviteCode") String inviteCode,
        @PathVariable("userId") int userId) {

        return ApiResponse.success(tempRoomService.exitTempRoom(inviteCode, userId));
    }

    @Operation(summary = "플레이어 프로필 수정", description = "플레이어는 대기방의 프로필을 수정할 수 있다.")

    @PutMapping("/{inviteCode}/profile")
    public ApiResponse<?> modifyPlayerProfile(@PathVariable("inviteCode") String inviteCode,
        @RequestBody PlayerDto playerDto) {

        return ApiResponse.success(
            Map.of("player", tempRoomService.modifyPlayerProfile(inviteCode, playerDto)));
    }

    @Operation(summary = "대기방의 방 설정 옵션 수정하기", description = "방장은 대기방의 설정 옵션을 수정할 수 있다.")
    @PatchMapping("/{inviteCode}/setting")
    public ApiResponse<?> modifyTempRoomSettingOption(@PathVariable("inviteCode") String inviteCode,
        @RequestBody SettingOptionDto settingOptionDto) {

        return ApiResponse.success(
            Map.of("setting", tempRoomService.modifyTempRoomSettingOption(inviteCode, settingOptionDto))
        );
    }

    @Operation(summary = "대기방 회원 강퇴", description = "대기방에서 유저를 강퇴시킵니다.")
    @PostMapping("/{inviteCode}/players/ban")
    public ApiResponse<?> addBanList(@PathVariable("inviteCode") String inviteCode, @RequestBody
        TempRoomBanRequestDto banRequestDto) {
        return ApiResponse.success(tempRoomService.addBanList(inviteCode, banRequestDto));
    }

    // Temp Start Call
    @Operation(summary = "[테스트용] 대기방에서 시작방 전환을 알립니다.", description = "대기방에서 시작방으로 전환되었는지 결과 리턴")
    @GetMapping("/{inviteCode}/check")
    public ApiResponse<?> callStartPoint(@PathVariable("inviteCode") String inviteCode) {
        return ApiResponse.success(
            Map.of("startCheck", tempRoomService.startCheck(inviteCode)));
    }

}
