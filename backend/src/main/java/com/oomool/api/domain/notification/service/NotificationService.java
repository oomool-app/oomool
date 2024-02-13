package com.oomool.api.domain.notification.service;

import static com.oomool.api.domain.notification.constant.NotificationType.*;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.oomool.api.domain.notification.constant.ReadState;
import com.oomool.api.domain.notification.dto.NotificationSaveRequestDto;
import com.oomool.api.domain.notification.dto.NotificationSearchRequestDto;
import com.oomool.api.domain.notification.dto.NotificationSearchResponseDto;
import com.oomool.api.domain.notification.dto.PushNotificationDto;
import com.oomool.api.domain.notification.entity.Notification;
import com.oomool.api.domain.notification.repository.NotificationRepository;
import com.oomool.api.domain.notification.util.NotificationMapper;
import com.oomool.api.domain.room.entity.GameRoom;
import com.oomool.api.domain.room.repository.GameRoomRepository;
import com.oomool.api.domain.user.entity.User;
import com.oomool.api.domain.user.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final GameRoomRepository gameRoomRepository;
    private final PushNotificationServiceImpl pushNotificationService;

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

    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void scheduledNotification() {
        log.info("Scheduled Notification started");

        LocalDate now = LocalDate.now();


        // 모든 게임방 중 현재 일자가 startDate와 endDate 사이에 있는 게임방을 조회
        List<GameRoom> gameRooms = gameRoomRepository.findAllByStartDateLessThanAndEndDateGreaterThan(
            now, now
        );

        log.info("GameRoom Count: {}", gameRooms.size());

        // gameRooms에 있는 모든 User를 users set에 담는다.
        Set<User> users = new HashSet<>();
        for (GameRoom gameRoom : gameRooms) {
            log.info("GameRoom: {}", gameRoom.getRoomUid());

            gameRoom.getPlayers().forEach(player -> {
                User user = player.getUser();
                log.info("User: {}", user);

                NotificationSaveRequestDto requestDto = NotificationSaveRequestDto.builder()
                    .userId(user.getId())
                    .type(SYSTEM)
                    .roomUid(gameRoom.getRoomUid())
                    .title(gameRoom.getTitle())
                    .body("새로운 질문이 등록되었어요!")
                    .build();

                saveNotification(requestDto);

                users.add(user);
            });
        }

        // 모든 user에게 푸시 알림을 보낸다.
        sendPushNotificationToUsers(users);
    }

    private void sendPushNotificationToUsers(Set<User> users) {
        for (User user : users) {
            PushNotificationDto pushNotificationDto = PushNotificationDto.builder()
                .userId(user.getId())
                .title("새로운 질문이 등록되었어요!")
                .body("알림을 눌러 지금 바로 확인해보세요!")
                .build();

            pushNotificationService.sendPushNotificationByUser(pushNotificationDto);
        }
    }
}
