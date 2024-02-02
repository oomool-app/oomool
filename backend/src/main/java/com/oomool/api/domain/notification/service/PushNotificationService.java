package com.oomool.api.domain.notification.service;

import java.util.List;

import com.oomool.api.domain.notification.dto.PushNotificationDto;
import com.oomool.api.domain.notification.dto.PushNotificationTokenDto;

public interface PushNotificationService {
    /**
     * 유저의 푸시 알림 토큰을 저장한다.
     *
     * @param userId 유저 pk
     * @param token 푸시 알림 토큰
     */
    void saveToken(int userId, String token);

    /**
     * 푸시 알림 토큰을 삭제한다.
     *
     * @param userId 유저 pk
     * @param token 푸시 알림 토큰
     */
    void removeToken(int userId, String token);

    /**
     * 유저의 모든 푸시 알림 토큰을 조회한다.
     *
     * @param userId 유저 pk
     */
    List<PushNotificationTokenDto> getAllTokensByUser(int userId);

    /**
     * 푸시 알림을 보낸다.
     *
     * @param pushNotificationDto 푸시 알림 정보
     */
    void sendPushNotification(PushNotificationDto pushNotificationDto);
}
