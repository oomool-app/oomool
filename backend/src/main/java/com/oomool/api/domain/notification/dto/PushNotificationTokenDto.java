package com.oomool.api.domain.notification.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "푸시 알림 토큰 DTO")
public record PushNotificationTokenDto(
    @Schema(description = "유저 ID", example = "1")
    int userId,

    @Schema(description = "푸시 알림 FCM 토큰", example = "YOUR_FCM_TOKEN")
    String token
) {
}
