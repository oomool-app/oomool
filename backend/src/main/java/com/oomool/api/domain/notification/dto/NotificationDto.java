package com.oomool.api.domain.notification.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 응답 : 알림 id, 유저 id, 문답방 uid, 알림 타입, 알림 제목, 알림 생성 시간, 읽은 시간
 */
@Getter
@Setter
@ToString
public class NotificationDto {
    private int notificationId;
    private int userId;
    private int gameRoomId;
    private String type;
    private String title;
    private LocalDateTime createAt;
    private LocalDateTime readAt;

    @Builder
    public NotificationDto(int notificationId, int userId, int gameRoomId, String type, String title,
        LocalDateTime createAt, LocalDateTime readAt) {
        this.notificationId = notificationId;
        this.userId = userId;
        this.gameRoomId = gameRoomId;
        this.type = type;
        this.title = title;
        this.createAt = createAt;
        this.readAt = readAt;
    }
}
