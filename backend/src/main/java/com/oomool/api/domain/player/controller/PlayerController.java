package com.oomool.api.domain.player.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oomool.api.domain.player.dto.GuessResultDto;
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

    @Operation(summary = "플레이어 전체 조회", description = "현재 문답방에 참여하고 있는 전체 플레이어 정보를 조회합니다.")
    @GetMapping("/{roomUID}")
    public ResponseEntity<?> getPlayerList(@PathVariable("roomUID") String roomUid) {
        return ResponseHandler.generateResponse(HttpStatus.OK, playerService.getPlayerDtoList(roomUid));
    }

    @Operation(summary = "플레이어의 마니띠 프로필 조회", description = "'나'와 나의 마니띠 프로필을 조회합니다.")
    @GetMapping("/{roomUID}/{userId}/manitti")
    public ResponseEntity<?> getPlayerManitti(@PathVariable("roomUID") String roomUid,
        @PathVariable("userId") int userId) {
        return ResponseHandler.generateResponse(HttpStatus.OK,
            playerService.getManittiPlayerProfile(roomUid, userId)
        );
    }

    @Operation(summary = "플레이어의 마니또 프로필 조회", description = "'나'는 나의 마니또 결과를 조회합니다.")
    @GetMapping("/{roomUID}/{userId}/manitto")
    public ResponseEntity<?> getMyManittoProfile(@PathVariable("roomUID") String roomUid,
        @PathVariable("userId") int userId) {
        return ResponseHandler.generateResponse(HttpStatus.OK, playerService.getManittoPlayerProfile(roomUid, userId));
    }

    @Operation(summary = "플레이어가 예측한 결과(성공, 실패) 저장", description = "나는 마니또를 예측하고 그 결과를 저장합니다.")
    @PostMapping("/{roomUID}/{userId}/guess")
    public ResponseEntity<?> saveGuessResult(@PathVariable("roomUID") String roomUid,
        @PathVariable("userId") int userId, @RequestBody GuessResultDto guessResult) {
        playerService.saveGuessResult(roomUid, userId, guessResult.getGuess());
        return ResponseHandler.generateResponse(HttpStatus.OK, "추측 결과를 저장했습니다.");
    }
}
