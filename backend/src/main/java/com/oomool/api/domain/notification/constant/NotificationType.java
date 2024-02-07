package com.oomool.api.domain.notification.constant;

import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "알림 타입")
public enum NotificationType {
    @Schema(description = "시스템 알림")
    SYSTEM,
    @Schema(description = "피드 작성 알림")
    WRITE_FEED;

    @JsonValue
    public String jsonString() {
        return this.toString().toLowerCase();
    }
}
