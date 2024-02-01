package com.oomool.api.domain.room.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oomool.api.domain.player.service.PlayerService;
import com.oomool.api.domain.room.service.GameRoomService;
import com.oomool.api.global.util.ResponseHandler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
@Tag(name = "문답방", description = "문답방 API를 명세합니다.")
public class GameRoomController {

    private final GameRoomService gameRoomService;
    private final PlayerService playerService;

    @Operation(summary = "문답방 생성 기능", description = "문답방을 생성합니다.")
    @PostMapping
    public ResponseEntity<?> createGameRoom(@RequestParam String inviteCode) throws Exception {
        // GameRoom Entity 저장 & roomUid 생성
        String roomUid = gameRoomService.createGameRoom(inviteCode);
        // Player Entity 저장
        playerService.savePlayerList(inviteCode, roomUid);
        return ResponseHandler.generateResponse(HttpStatus.OK, Map.of("roomUid", roomUid));
    }

    @Operation(summary = "문답방 상세 정보 조회 기능", description = "문답방을 조회합니다.")
    @GetMapping("/{roomUID}")
    public ResponseEntity<?> getGameRoom(@PathVariable("roomUID") String roomUid) {
        return ResponseHandler.generateResponse(HttpStatus.OK, gameRoomService.getGameRoom(roomUid));
    }

}
