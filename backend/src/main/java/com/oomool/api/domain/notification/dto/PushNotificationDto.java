package com.oomool.api.domain.notification.dto;

import lombok.Builder;

@Builder
public record PushNotificationDto(
    int userId,
    String title,
    String body
) {
}
