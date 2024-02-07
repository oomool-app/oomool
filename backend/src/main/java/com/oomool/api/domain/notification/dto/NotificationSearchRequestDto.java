package com.oomool.api.domain.notification.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "알림 검색 요청 DTO")
public record NotificationSearchRequestDto(
    @Schema(description = "알림 ID", example = "1")
    int userId
) {
}
