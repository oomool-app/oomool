package com.oomool.api.domain.notification.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.oomool.api.domain.notification.dto.PushNotificationDto;
import com.oomool.api.domain.notification.dto.PushNotificationTokenDto;
import com.oomool.api.domain.notification.entity.PushNotificationToken;
import com.oomool.api.domain.notification.repository.PushNotificationTokenRepository;
import com.oomool.api.domain.user.entity.User;
import com.oomool.api.domain.user.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PushNotificationServiceImpl implements PushNotificationService {

    private final PushNotificationTokenRepository pushNotificationTokenRepository;
    private final UserRepository userRepository;
    private final FirebaseMessaging firebaseMessaging;

    @Override
    @Transactional
    public void saveToken(int userId, String token) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("해당 유저가 존재하지 않습니다."));

        // 기존 토큰이 존재한다면(기기 로그인 변경 등 issue 발생 시) 삭제 후 재등록
        Optional<PushNotificationToken> optionalToken = pushNotificationTokenRepository.findByToken(token);

        if (optionalToken.isPresent()) {
            PushNotificationToken exist = optionalToken.get();
            if (exist.getUser().getId() == userId) {
                // 토큰이 존재하고, 토큰의 사용자 ID가 입력된 사용자 ID와 일치하므로 메서드를 종료
                return;
            }
            // 토큰이 존재하고, 토큰의 사용자 ID가 입력된 사용자 ID와 일치하지 않으므로 토큰을 삭제
            pushNotificationTokenRepository.delete(exist);
        }

        PushNotificationToken pushNotificationToken = PushNotificationToken.builder()
            .user(user)
            .token(token)
            .build();

        pushNotificationTokenRepository.save(pushNotificationToken);
    }

    @Override
    public void removeToken(String token) {
        PushNotificationToken pushNotificationToken = pushNotificationTokenRepository
            .findByToken(token)
            .orElseThrow(() -> new EntityNotFoundException("해당 토큰이 존재하지 않습니다."));

        pushNotificationTokenRepository.delete(pushNotificationToken);
    }

    @Override
    public List<PushNotificationTokenDto> getAllTokensByUser(int userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("해당 유저가 존재하지 않습니다."));

        List<PushNotificationToken> tokens = pushNotificationTokenRepository.findAllByUser(user);

        return tokens.stream()
            .map(token -> PushNotificationTokenDto.builder()
                .userId(userId)
                .token(token.getToken())
                .build())
            .collect(Collectors.toList());
    }

    @Override
    public void sendPushNotificationByUser(PushNotificationDto pushNotificationDto) {
        User user = userRepository.findById(pushNotificationDto.userId())
            .orElseThrow(() -> new EntityNotFoundException("해당 유저가 존재하지 않습니다."));

        List<PushNotificationToken> tokens = pushNotificationTokenRepository.findAllByUser(user);

        if (tokens.isEmpty()) {
            throw new EntityNotFoundException("해당 유저의 푸시 알림 토큰이 존재하지 않습니다.");
        }

        for (PushNotificationToken token : tokens) {
            // 푸시 알림을 보내는 로직
            Notification notification = Notification.builder()
                .setTitle(pushNotificationDto.title())
                .setBody(pushNotificationDto.body())
                .build();

            Message message = Message.builder()
                .setNotification(notification)
                .setToken(token.getToken())
                .build();

            try {
                firebaseMessaging.send(message);
            } catch (FirebaseMessagingException e) {
                log.error("푸시 알림을 보내는 중 오류가 발생했습니다.", e);
            }
        }

        System.out.println(pushNotificationDto);
    }
}
