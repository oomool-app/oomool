package com.oomool.api.domain.notification.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oomool.api.domain.notification.dto.NotificationDto;
import com.oomool.api.domain.notification.service.NotificationService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
public class NotificationController {

    private final NotificationService notificationService;

    /**
     * 응답 : 알림 id, 유저 id, 문답방 uid, 알림 타입, 알림 제목, 알림 생성 시간, 읽은 시간
     *
     * TODO : 공통 response Exception 처리
     */
    @Operation(summary = "유저의 모든 알림 조회", description = "유저 id를 기반으로 유저의 모든 알림을 조회하는 API")
    @GetMapping("/{userId}")
    public ResponseEntity<List<NotificationDto>> findAllNotification(@PathVariable("userId") int userId) {

        List<NotificationDto> notificationDtoList = notificationService.getAllNotificationByUserId(userId);

        return new ResponseEntity<>(notificationDtoList, HttpStatus.OK);
    }

    /**
     * 응답 : fail, success message
     */
    @Operation(summary = "단건 알림 조회.", description = "유저가 알림을 클릭했을 때 읽은 시간을 DB에 추가해주는 API")
    @PostMapping("/{notificationId}")
    public ResponseEntity<String> createReadAt(@PathVariable("notificationId") String norificationId) {
        String message = new String();

        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
