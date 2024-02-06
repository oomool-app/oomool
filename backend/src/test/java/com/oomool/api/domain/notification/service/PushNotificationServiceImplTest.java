package com.oomool.api.domain.notification.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.oomool.api.domain.notification.dto.PushNotificationTokenDto;
import com.oomool.api.domain.notification.entity.PushNotificationToken;
import com.oomool.api.domain.notification.repository.PushNotificationTokenRepository;
import com.oomool.api.domain.user.entity.User;
import com.oomool.api.domain.user.repository.UserRepository;

public class PushNotificationServiceImplTest {

    @InjectMocks
    private PushNotificationServiceImpl pushNotificationService;

    @Mock
    private PushNotificationTokenRepository pushNotificationTokenRepository;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void saveTokenTest() {
        // Given
        // 테스트에 필요한 User와 PushNotificationToken 객체를 생성합니다.
        int userId = 1;
        String token = "token";
        User user = User.builder()
            .id(userId)
            .email("email")
            .username("username")
            .build();

        PushNotificationToken pushNotificationToken = PushNotificationToken.builder()
            .user(user)
            .token(token)
            .build();

        // PushNotificationTokenRepository의 save 메소드가 호출되면 pushNotificationToken을 반환하도록 설정합니다.
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(pushNotificationTokenRepository.save(any(PushNotificationToken.class))).thenReturn(pushNotificationToken);

        // When
        // saveToken 메소드를 호출합니다.
        pushNotificationService.saveToken(userId, token);

        // Then
        // save 메소드가 한 번 호출되었는지 검증합니다.
        verify(pushNotificationTokenRepository, times(1)).save(any(PushNotificationToken.class));
    }

    @Test
    public void removeTokenTest() {
        // Given
        // 테스트에 필요한 User와 PushNotificationToken 객체를 생성합니다.
        int userId = 1;
        String token = "token";
        User user = new User(userId, "email", "username", null, null);
        PushNotificationToken pushNotificationToken = PushNotificationToken.builder()
            .user(user)
            .token(token)
            .build();

        // PushNotificationTokenRepository의 findByUserAndToken 메소드가 호출되면 pushNotificationToken을 반환하도록 설정합니다.
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(pushNotificationTokenRepository.findByUserAndToken(user, token)).thenReturn(
            Optional.of(pushNotificationToken));

        // When
        // removeToken 메소드를 호출합니다.
        pushNotificationService.removeToken(token);

        // Then
        // delete 메소드가 한 번 호출되었는지 검증합니다.
        verify(pushNotificationTokenRepository, times(1)).delete(pushNotificationToken);
    }

    @Test
    public void getAllTokensByUserTest() {
        // Given
        // 테스트에 필요한 User와 PushNotificationToken 객체를 생성합니다.
        int userId = 1;
        User user = User.builder()
            .id(userId)
            .email("email")
            .username("username")
            .build();
        PushNotificationToken pushNotificationToken1 = PushNotificationToken.builder()
            .user(user)
            .token("token1")
            .build();
        PushNotificationToken pushNotificationToken2 = PushNotificationToken.builder()
            .user(user)
            .token("token2")
            .build();
        List<PushNotificationToken> pushNotificationTokens = Arrays.asList(pushNotificationToken1,
            pushNotificationToken2);

        // PushNotificationTokenRepository의 findAllByUser 메소드가 호출되면 pushNotificationTokens를 반환하도록 설정합니다.
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(pushNotificationTokenRepository.findAllByUser(user)).thenReturn(pushNotificationTokens);

        // When
        List<PushNotificationTokenDto> result = pushNotificationService.getAllTokensByUser(userId);

        // Then
        // 반환된 토큰 리스트가 예상한 리스트와 같은지 검증합니다.
        assertEquals(2, result.size());
        assertEquals("token1", result.get(0).token());
        assertEquals("token2", result.get(1).token());
    }
}
