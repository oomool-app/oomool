package com.oomool.api.domain.notification.dto;

import lombok.Builder;

@Builder
public record PushNotificationTokenDto(
    int userId,
    String token
) {
}
