package com.oomool.api.domain.notification.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.oomool.api.domain.notification.dto.NotificationDto;
import com.oomool.api.domain.notification.entity.Notification;
import com.oomool.api.domain.notification.repository.NotificationReposiitory;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class NotificationService {
    private NotificationReposiitory notificationReposiitory;

    public List<NotificationDto> getAllNotificationByUserId(int userId) {
        List<NotificationDto> notificationDtoList = new ArrayList<>();

        List<Notification> notificationList = notificationReposiitory.findByUserId(userId);

        for (Notification notification : notificationList) {
            NotificationDto notificationDto = NotificationDto.builder()
                .createAt(notification.getCreateAt())
                .gameRoomId(notification.getGameRoom().getId())
                .readAt(notification.getReadAt())
                .title(notification.getTitle())
                .notificationId(notification.getId())
                .type(notification.getType())
                .userId(notification.getUser().getId())
                .build();

            notificationDtoList.add(notificationDto);
        }

        log.info("notificationDtoList : " + notificationDtoList.toString());

        return notificationDtoList;
    }
}
