package com.oomool.api.domain.room.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oomool.api.domain.room.dto.RequestDto;
import com.oomool.api.domain.room.service.RecentViewService;
import com.oomool.api.global.util.ResponseHandler;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/view")
@RequiredArgsConstructor
public class RecentViewController {

    private final RecentViewService recentViewService;

    @Operation(summary = "대기방 조회")
    @PatchMapping("/temps")
    public ResponseEntity<?> viewTempRoom(@RequestBody RequestDto requestDto) {
        return ResponseHandler.generateResponse(HttpStatus.OK,
            recentViewService.recentlyViewRoom("tempView", requestDto.getCode(), requestDto.getUserId()));
    }

    @Operation(summary = "문답방 조회")
    @PatchMapping("/games")
    public ResponseEntity<?> viewGameRoom(@RequestBody RequestDto requestDto) {
        return ResponseHandler.generateResponse(HttpStatus.OK,
            recentViewService.recentlyViewRoom("gameView", requestDto.getCode(), requestDto.getUserId()));
    }
}
