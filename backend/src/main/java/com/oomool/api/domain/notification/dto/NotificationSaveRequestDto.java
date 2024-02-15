package com.oomool.api.domain.notification.dto;

import com.oomool.api.domain.notification.constant.NotificationType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "알림 저장 요청 DTO")
public record NotificationSaveRequestDto(

    @Schema(description = "유저 ID", example = "1")
    int userId,

    @Schema(description = "알림 타입", example = "system")
    NotificationType type,

    @Schema(description = "게임방 UID", example = "qi12Qio6")
    String roomUid,

    @Schema(description = "알림 제목", example = "알림 제목입니다.")
    String title,

    @Schema(description = "알림 내용", example = "알림 내용입니다.")
    String body
) {
}
