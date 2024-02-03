package com.oomool.api.domain.player.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oomool.api.domain.player.service.PlayerServiceImpl;
import com.oomool.api.global.util.ResponseHandler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/players")
@RequiredArgsConstructor
@Tag(name = "플레이어", description = "플레이어 API를 명세합니다.")
public class PlayerController {

    private final PlayerServiceImpl playerService;

    @Operation(summary = "플레이어 조회 기능", description = "플레이어를 생성합니다.")
    @GetMapping("/{roomUID}")
    public ResponseEntity<?> getPlayerList(@PathVariable("roomUID") String roomUid) {
        return ResponseHandler.generateResponse(HttpStatus.OK,
            Map.of("players", playerService.getPlayerDtoList(roomUid)));
    }

}
