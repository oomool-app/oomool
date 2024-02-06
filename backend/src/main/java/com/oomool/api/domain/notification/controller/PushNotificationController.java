package com.oomool.api.domain.notification.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oomool.api.domain.notification.dto.PushNotificationDto;
import com.oomool.api.domain.notification.dto.PushNotificationTokenDto;
import com.oomool.api.domain.notification.service.PushNotificationService;
import com.oomool.api.global.util.ResponseHandler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/push-notifications")
@RequiredArgsConstructor
@Tag(name = "푸시 알림", description = "푸시 알림 API를 명세합니다.")
public class PushNotificationController {

    private final PushNotificationService pushNotificationService;

    // 토큰 저장에 대한 API 생성
    @PostMapping("/token")
    @Operation(summary = "푸시 알림 토큰 저장 기능", description = "푸시 알림 토큰을 저장합니다.")
    public ResponseEntity<?> saveToken(@RequestBody PushNotificationTokenDto pushNotificationTokenDto) {
        pushNotificationService.saveToken(pushNotificationTokenDto.userId(), pushNotificationTokenDto.token());

        return ResponseHandler.generateResponse(HttpStatus.CREATED, "푸시 알림 토큰이 저장되었습니다.");
    }

    // 토큰 삭제에 대한 API 생성
    @DeleteMapping("/token")
    @Operation(summary = "푸시 알림 토큰 삭제 기능", description = "푸시 알림 토큰을 삭제합니다.")
    public ResponseEntity<?> removeToken(
        @RequestParam(name = "token") String token
    ) {
        pushNotificationService.removeToken(token);

        return ResponseHandler.generateResponse(HttpStatus.OK, "푸시 알림 토큰이 삭제되었습니다.");
    }

    // 유저의 모든 토큰 조회에 대한 API 생성
    @GetMapping("/token/{userId}")
    @Operation(summary = "푸시 알림 토큰 조회 기능", description = "유저의 모든 푸시 알림 토큰을 조회합니다.")
    public ResponseEntity<?> getAllTokensByUser(@PathVariable("userId") int userId) {

        return ResponseHandler.generateResponse(HttpStatus.OK,
            Map.of("tokens", pushNotificationService.getAllTokensByUser(userId)));
    }

    // 푸시 알림 전송에 대한 API 생성
    @PostMapping
    @Operation(summary = "푸시 알림 전송 기능", description = "해당하는 유저에게 푸시 알림을 보냅니다.")
    public ResponseEntity<?> sendPushNotificationByUser(
        @RequestBody PushNotificationDto pushNotificationDto) {
        pushNotificationService.sendPushNotificationByUser(pushNotificationDto);

        return ResponseHandler.generateResponse(HttpStatus.OK,
            pushNotificationDto);
    }
}
