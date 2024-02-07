package com.oomool.api.domain.notification.service;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.oomool.api.domain.notification.constant.ReadState;
import com.oomool.api.domain.notification.dto.NotificationSaveRequestDto;
import com.oomool.api.domain.notification.dto.NotificationSearchRequestDto;
import com.oomool.api.domain.notification.dto.NotificationSearchResponseDto;
import com.oomool.api.domain.notification.entity.Notification;
import com.oomool.api.domain.notification.repository.NotificationRepository;
import com.oomool.api.domain.notification.util.NotificationMapper;
import com.oomool.api.domain.room.entity.GameRoom;
import com.oomool.api.domain.room.repository.GameRoomRepository;
import com.oomool.api.domain.user.entity.User;
import com.oomool.api.domain.user.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final GameRoomRepository gameRoomRepository;

    /**
     * 알림 조회
     * <p>
     * 유저 알림을 조회한다.
     *
     * @param user 알림을 조회하고자 하는 유저 정보
     */
    @Transactional
    public Map<ReadState, List<NotificationSearchResponseDto>> findNotifications(NotificationSearchRequestDto user) {
        List<Notification> notifications = notificationRepository.findAllByUserId(user.userId());

        return getSortedNotifications(notifications);
    }

    /**
     * 새 알림 여부 조회
     * <p>
     * 유저에게 새로운 알림이 있는지 조회한다.
     *
     * @param userId 알림을 조회하고자 하는 유저 정보
     * @return 새 알림 여부
     */
    public boolean hasNewNotification(int userId) {
        return notificationRepository.existsByUserIdAndReadAtIsNull(userId);
    }

    /**
     * 알림 저장
     * <p>
     * 유저에게 보낸 알림을 저장한다.
     *
     * @param requestDto 알림 저장 요청 정보
     */
    public void saveNotification(NotificationSaveRequestDto requestDto) {
        User user = userRepository.findById(requestDto.userId()).orElseThrow(
            () -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다.")
        );
        GameRoom gameRoom = gameRoomRepository.findByRoomUid(requestDto.roomUid()).orElseThrow(
            () -> new IllegalArgumentException("해당 방을 찾을 수 없습니다.")
        );
        Notification notification = NotificationMapper.toEntity(requestDto, user, gameRoom);

        notificationRepository.save(notification);
    }

    /**
     * 알림 분류 메서드
     * <p>
     * 읽음/안읽음 상태에 따라 알림을 분류하여 리턴한다.
     *
     * @param notifications 알림 목록
     * @return 읽음/안읽음 상태에 따라 분류된 알림 목록
     */
    private EnumMap<ReadState, List<NotificationSearchResponseDto>> getSortedNotifications(
        List<Notification> notifications) {

        Map<Boolean, List<Notification>> partitionedNotifications = notifications.stream()
            .collect(Collectors.partitioningBy(Notification::isRead));

        List<Notification> readNotifications = partitionedNotifications.get(true);
        List<Notification> unreadNotifications = partitionedNotifications.get(false);

        EnumMap<ReadState, List<NotificationSearchResponseDto>> result = new EnumMap<>(ReadState.class);

        result.put(ReadState.READ, convertToDto(readNotifications));
        result.put(ReadState.UNREAD, convertToDto(unreadNotifications));

        // 읽음 처리
        unreadNotifications.forEach(Notification::updateReadAt);

        return result;
    }

    /**
     * 알림 DTO 변환 메서드
     * <p>
     * 알림 엔티티를 DTO로 변환한다.
     *
     * @param notifications 알림 목록
     * @return 알림 DTO 목록
     */
    private List<NotificationSearchResponseDto> convertToDto(List<Notification> notifications) {
        return notifications.stream()
            .map(NotificationSearchResponseDto::of)
            .collect(Collectors.toList());
    }
}
