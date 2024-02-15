package com.oomool.api.domain.notification.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "푸시 알림 DTO")
public record PushNotificationDto(
    @Schema(description = "유저 ID", example = "1")
    int userId,

    @Schema(description = "알림 제목", example = "알림 제목입니다.")
    String title,

    @Schema(description = "알림 내용", example = "알림 내용입니다.")
    String body
) {
}
