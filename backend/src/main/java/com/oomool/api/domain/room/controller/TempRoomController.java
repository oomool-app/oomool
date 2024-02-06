package com.oomool.api.domain.room.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.oomool.api.domain.player.dto.PlayerDto;
import com.oomool.api.domain.room.dto.TempRoomRequestDto;
import com.oomool.api.domain.room.service.TempRoomRedisService;
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

    private final TempRoomRedisService tempRoomRedisService;
    private final UserService userService;

    @Operation(summary = "대기방 생성 기능", description = "대기방을 생성합니다.")
    @PostMapping
    public ResponseEntity<?> createTempRoom(@RequestBody TempRoomRequestDto request) throws JsonProcessingException {

        int masterId = userService.getUserIdByEmail(request.getMaster().getEmail());

        return ResponseHandler.generateResponse(HttpStatus.OK,
            tempRoomRedisService.createTempRoom(request.getSetting(), masterId));
    }

    @Operation(summary = "대기방 조회 기능")
    @GetMapping("/{inviteCode}")
    public ResponseEntity<?> getTemRoom(@PathVariable("inviteCode") String inviteCode) throws JsonProcessingException {

        return ResponseHandler.generateResponse(HttpStatus.OK, tempRoomRedisService.getTempRoom(inviteCode));
    }

    @Operation(summary = "대기방 입장")
    @PostMapping("/{inviteCode}/players")
    public ResponseEntity<?> joinTempRoom(@PathVariable("inviteCode") String inviteCode,
        @RequestBody PlayerDto player) throws JsonProcessingException {

        return ResponseHandler.generateResponse(HttpStatus.OK, tempRoomRedisService.joinTempRoom(inviteCode, player));
    }

    @Operation(summary = "대기방 삭제", description = "방장은 대기방을 삭제할 수 있다.")
    @DeleteMapping("/{inviteCode}")
    public ResponseEntity<?> deleteTempRoom(@PathVariable("inviteCode") String inviteCode,
        @RequestParam("id") int userId) {
        return ResponseHandler.generateResponse(HttpStatus.OK, tempRoomRedisService.deleteTempRoom(inviteCode, userId));
    }

    @Operation(summary = "플레이어 퇴장 ", description = "플레이어는 대기방을 퇴장할 수 있다.")
    @DeleteMapping("/{inviteCode}/{userId}")
    public ResponseEntity<?> exitTempRoom(@PathVariable("inviteCode") String inviteCode,
        @PathVariable("userId") int userId) {
        return ResponseHandler.generateResponse(HttpStatus.OK, tempRoomRedisService.exitTempRoom(inviteCode, userId));
    }

    @Operation(summary = "플레이어 프로필 수정", description = "플레이어는 대기방의 프로필을 수정할 수 있다.")
    @PutMapping("/{inviteCode}")
    public ResponseEntity<?> modifyPlayerProfile(@PathVariable("inviteCode") String inviteCode,
        @RequestBody PlayerDto playerDto) throws JsonProcessingException {
        return ResponseHandler.generateResponse(HttpStatus.OK,
            Map.of("player", tempRoomRedisService.modifyPlayerProfile(inviteCode, playerDto)));
    }
}
