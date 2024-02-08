package com.oomool.api.domain.room.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oomool.api.domain.room.dto.InviteCodeDto;
import com.oomool.api.domain.room.service.GameRoomServiceImpl;
import com.oomool.api.global.util.ResponseHandler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
@Tag(name = "문답방", description = "문답방 API를 명세합니다.")
public class GameRoomController {

    private final GameRoomServiceImpl gameRoomService;

    @Operation(summary = "문답방 생성 기능", description = "문답방을 생성합니다.")
    @PostMapping
    public ResponseEntity<?> createGameRoom(@RequestBody InviteCodeDto inviteCodeDto) throws Exception {
        return ResponseHandler.generateResponse(HttpStatus.OK,
            gameRoomService.createGameRoom(inviteCodeDto.getInviteCode()));
    }

    @Operation(summary = "문답방 상세 정보 조회 기능", description = "문답방의 상세정보를 조회합니다.")
    @GetMapping("/{roomUID}")
    public ResponseEntity<?> getGameRoomDetail(@PathVariable("roomUID") String roomUid) {
        return ResponseHandler.generateResponse(HttpStatus.OK, gameRoomService.getGameRoomDetail(roomUid));
    }

}
