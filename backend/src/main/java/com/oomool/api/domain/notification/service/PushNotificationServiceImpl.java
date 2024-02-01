package com.oomool.api.domain.notification.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.oomool.api.domain.notification.dto.PushNotificationDto;
import com.oomool.api.domain.notification.entity.PushNotificationToken;
import com.oomool.api.domain.notification.repository.PushNotificationTokenRepository;
import com.oomool.api.domain.user.entity.User;
import com.oomool.api.domain.user.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PushNotificationServiceImpl implements PushNotificationService {

    private final PushNotificationTokenRepository pushNotificationTokenRepository;
    private final UserRepository userRepository;

    @Override
    public void saveToken(int userId, String token) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("해당 유저가 존재하지 않습니다."));

        PushNotificationToken pushNotificationToken = PushNotificationToken.builder()
            .user(user)
            .token(token)
            .build();

        pushNotificationTokenRepository.save(pushNotificationToken);
    }

    @Override
    public void removeToken(int userId, String token) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("해당 유저가 존재하지 않습니다."));

        PushNotificationToken pushNotificationToken = pushNotificationTokenRepository.findByUserAndToken(user, token)
            .orElseThrow(() -> new EntityNotFoundException("해당 토큰이 존재하지 않습니다."));

        pushNotificationTokenRepository.delete(pushNotificationToken);
    }

    @Override
    public List<PushNotificationToken> getAllTokensByUser(int userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("해당 유저가 존재하지 않습니다."));

        return pushNotificationTokenRepository.findAllByUser(user);
    }

    @Override
    public void sendPushNotification(PushNotificationDto pushNotificationDto) {
        System.out.println(pushNotificationDto);
    }
}
