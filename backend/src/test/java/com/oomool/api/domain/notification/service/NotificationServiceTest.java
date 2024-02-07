package com.oomool.api.domain.notification.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.oomool.api.domain.notification.constant.ReadState;
import com.oomool.api.domain.notification.dto.NotificationSaveRequestDto;
import com.oomool.api.domain.notification.dto.NotificationSearchRequestDto;
import com.oomool.api.domain.notification.dto.NotificationSearchResponseDto;
import com.oomool.api.domain.notification.entity.Notification;
import com.oomool.api.domain.notification.repository.NotificationRepository;
import com.oomool.api.domain.room.entity.GameRoom;
import com.oomool.api.domain.room.repository.GameRoomRepository;
import com.oomool.api.domain.user.entity.User;
import com.oomool.api.domain.user.repository.UserRepository;

public class NotificationServiceTest {

    @InjectMocks
    private NotificationService notificationService;

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private GameRoomRepository gameRoomRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveNotification() {
        // given
        NotificationSaveRequestDto requestDto = new NotificationSaveRequestDto(1, null, "roomUid", "title", "body");
        User user = new User();
        GameRoom gameRoom = new GameRoom();
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));
        when(gameRoomRepository.findByRoomUid(anyString())).thenReturn(Optional.of(gameRoom));
        when(notificationRepository.save(any(Notification.class))).thenReturn(null);

        // when
        notificationService.saveNotification(requestDto);

        // then
        verify(userRepository, times(1)).findById(anyInt());
        verify(gameRoomRepository, times(1)).findByRoomUid(anyString());
        verify(notificationRepository, times(1)).save(any(Notification.class));
    }

    @Test
    public void testFindNotifications() {
        // given
        NotificationSearchRequestDto requestDto = new NotificationSearchRequestDto(1);
        List<Notification> notifications = new ArrayList<>();
        when(notificationRepository.findAllByUserId(anyInt())).thenReturn(notifications);

        // when
        Map<ReadState, List<NotificationSearchResponseDto>> result = notificationService.findNotifications(requestDto);

        // then
        verify(notificationRepository, times(1)).findAllByUserId(1);
        assertNotNull(result);
    }
}
