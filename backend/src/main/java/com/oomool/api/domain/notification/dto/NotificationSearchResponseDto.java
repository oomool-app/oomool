package com.oomool.api.domain.notification.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.oomool.api.domain.notification.constant.NotificationType;
import com.oomool.api.domain.notification.entity.Notification;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "알림 검색 응답 DTO")
public record NotificationSearchResponseDto(
    @Schema(description = "게임방 UID", example = "qi12Qio6")
    String roomUid,

    @Schema(description = "알림 타입")
    NotificationType type,

    @Schema(description = "알림 제목", example = "알림 제목입니다.")
    String title,

    @Schema(description = "알림 내용", example = "알림 내용입니다.")
    String body,

    @Schema(description = "알림 생성일시", example = "2021-07-01T00:00:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    LocalDateTime createdAt
) {
    public static NotificationSearchResponseDto of(Notification notification) {
        return NotificationSearchResponseDto.builder()
            .roomUid(notification.getGameRoom().getRoomUid())
            .type(notification.getType())
            .title(notification.getTitle())
            .body(notification.getBody())
            .createdAt(notification.getCreatedAt())
            .build();
    }
}
