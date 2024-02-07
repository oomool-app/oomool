package com.oomool.api.domain.notification.entity;

import static jakarta.persistence.FetchType.*;
import static lombok.AccessLevel.*;

import java.time.LocalDateTime;

import com.oomool.api.domain.notification.constant.NotificationType;
import com.oomool.api.domain.room.entity.GameRoom;
import com.oomool.api.domain.user.entity.User;
import com.oomool.api.global.entity.BaseTimeEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class Notification extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "game_room_id")
    private GameRoom gameRoom;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    private String title;

    private String body;

    private LocalDateTime readAt;

    @Builder
    public Notification(User user, GameRoom gameRoom, NotificationType type, String title, String body) {
        this.user = user;
        this.gameRoom = gameRoom;
        this.type = type;
        this.title = title;
        this.body = body;
    }

    public void updateReadAt() {
        if (this.readAt == null) {
            this.readAt = LocalDateTime.now();
        }
    }

    public boolean isRead() {
        return this.readAt != null;
    }

}
