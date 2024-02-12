package com.oomool.api.domain.notification.dto.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oomool.api.domain.notification.constant.ReadState;
import com.oomool.api.domain.notification.dto.NotificationSaveRequestDto;
import com.oomool.api.domain.notification.dto.NotificationSearchRequestDto;
import com.oomool.api.domain.notification.dto.NotificationSearchResponseDto;
import com.oomool.api.domain.notification.service.NotificationService;
import com.oomool.api.global.dto.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/notifications")
@Tag(name = "알림", description = "알림 관련 API를 명세합니다.")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping("/{userId}")
    @Operation(summary = "유저의 알림 조회", description = "유저의 모든 알림을 조회합니다.")
    public ApiResponse<?> getNotifications(@PathVariable int userId) {
        NotificationSearchRequestDto requestDto = new NotificationSearchRequestDto(userId);
        Map<ReadState, List<NotificationSearchResponseDto>> notifications = notificationService.findNotifications(
            requestDto);

        return ApiResponse.success(notifications);
    }

    @GetMapping("/{userId}/new")
    @Operation(summary = "새 알림 여부 조회", description = "유저에게 새로운 알림이 있는지 조회합니다.")
    public ApiResponse<?> hasNewNotification(@PathVariable int userId) {
        boolean hasNewNotification = notificationService.hasNewNotification(userId);

        return ApiResponse.success(String.valueOf(hasNewNotification));
    }

    @PostMapping
    @Operation(summary = "알림 저장", description = "유저의 알림을 저장합니다.")
    public ApiResponse<?> saveNotification(@RequestBody NotificationSaveRequestDto requestDto) {
        notificationService.saveNotification(requestDto);

        return ApiResponse.success("알림이 저장되었습니다.");
    }
}
