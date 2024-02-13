package com.oomool.api.domain.room.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.oomool.api.domain.player.dto.PlayerDto;
import com.oomool.api.domain.room.dto.SettingOptionDto;
import com.oomool.api.domain.room.dto.TempRoomBanRequestDto;
import com.oomool.api.domain.room.dto.TempRoomRequestDto;
import com.oomool.api.domain.room.service.TempRoomService;
import com.oomool.api.domain.user.service.UserService;
import com.oomool.api.global.util.ResponseHandler;

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
    public ResponseEntity<?> createTempRoom(@RequestBody TempRoomRequestDto request) {

        int masterId = userService.getUserIdByEmail(request.getMaster().getEmail());

        return ResponseHandler.generateResponse(HttpStatus.OK,
            tempRoomService.createTempRoom(request.getSetting(), masterId));
    }

    @Operation(summary = "대기방 조회 기능")
    @GetMapping("/{inviteCode}")
    public ResponseEntity<?> getTempRoom(@PathVariable("inviteCode") String inviteCode) {

        return ResponseHandler.generateResponse(HttpStatus.OK, tempRoomService.getTempRoomDetail(inviteCode));
    }

    @Operation(summary = "대기방 입장")
    @PostMapping("/{inviteCode}/players")
    public ResponseEntity<?> joinTempRoom(@PathVariable("inviteCode") String inviteCode,
        @RequestBody PlayerDto player) {

        return ResponseHandler.generateResponse(HttpStatus.OK, tempRoomService.joinTempRoom(inviteCode, player));
    }

    @Operation(summary = "대기방 삭제", description = "방장은 대기방을 삭제할 수 있다.")
    @DeleteMapping("/{inviteCode}")
    public ResponseEntity<?> deleteTempRoom(@PathVariable("inviteCode") String inviteCode,
        @RequestParam("id") int userId) {

        return ResponseHandler.generateResponse(HttpStatus.OK, tempRoomService.deleteTempRoom(inviteCode, userId));
    }

    @Operation(summary = "플레이어 퇴장 ", description = "플레이어는 대기방을 퇴장할 수 있다.")
    @DeleteMapping("/{inviteCode}/{userId}")
    public ResponseEntity<?> exitTempRoom(@PathVariable("inviteCode") String inviteCode,
        @PathVariable("userId") int userId) {

        return ResponseHandler.generateResponse(HttpStatus.OK, tempRoomService.exitTempRoom(inviteCode, userId));
    }

    @Operation(summary = "플레이어 프로필 수정", description = "플레이어는 대기방의 프로필을 수정할 수 있다.")
    @PutMapping("/{inviteCode}/profile")
    public ResponseEntity<?> modifyPlayerProfile(@PathVariable("inviteCode") String inviteCode,
        @RequestBody PlayerDto playerDto) {

        return ResponseHandler.generateResponse(HttpStatus.OK,
            Map.of("player", tempRoomService.modifyPlayerProfile(inviteCode, playerDto)));
    }

    @Operation(summary = "대기방의 방 설정 옵션 수정하기", description = "방장은 대기방의 설정 옵션을 수정할 수 있다.")
    @PatchMapping("/{inviteCode}/setting")
    public ResponseEntity<?> modifyTempRoomSettingOption(@PathVariable("inviteCode") String inviteCode,
        @RequestBody SettingOptionDto settingOptionDto) {

        return ResponseHandler.generateResponse(HttpStatus.OK,
            Map.of("setting", tempRoomService.modifyTempRoomSettingOption(inviteCode, settingOptionDto))
        );
    }

    @Operation(summary = "대기방 회원 강퇴", description = "대기방에서 유저를 강퇴시킵니다.")
    @PostMapping("/{inviteCode}/players/ban")
    public ResponseEntity<?> addBanList(@PathVariable("inviteCode") String inviteCode, @RequestBody
        TempRoomBanRequestDto banRequestDto) {
        return ResponseHandler.generateResponse(HttpStatus.OK, tempRoomService.addBanList(inviteCode, banRequestDto));
    }

    // Temp Start Call
    @Operation(summary = "[테스트용] 대기방에서 시작방 전환을 알립니다.", description = "대기방에서 시작방으로 전환되었는지 결과 리턴")
    @GetMapping("/{inviteCode}/check")
    public ResponseEntity<?> callStartPoint(@PathVariable("inviteCode") String inviteCode) {
        return ResponseHandler.generateResponse(HttpStatus.OK,
            Map.of("startCheck", tempRoomService.startCheck(inviteCode)));
    }

    // Temp Start Connect Call
    @Operation(summary = "[테스트] 대기방에서 플레이어 입장을 확인합니다.", description = "SSE 테스트")
    @GetMapping(path = "/{inviteCode}/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter connect(@PathVariable("inviteCode") String inviteCode) {
        return tempRoomService.connection(inviteCode);
    }

}
