package com.oomool.api.domain.notification.util;

import com.oomool.api.domain.notification.dto.NotificationSaveRequestDto;
import com.oomool.api.domain.notification.entity.Notification;
import com.oomool.api.domain.room.entity.GameRoom;
import com.oomool.api.domain.user.entity.User;

public class NotificationMapper {

    public static Notification toEntity(NotificationSaveRequestDto dto, User user, GameRoom gameRoom) {
        return Notification.builder()
            .user(user)
            .title(dto.title())
            .body(dto.body())
            .type(dto.type())
            .gameRoom(gameRoom)
            .build();
    }
}
