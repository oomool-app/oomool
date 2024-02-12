package com.oomool.api.domain.notification.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oomool.api.domain.notification.entity.PushNotificationToken;
import com.oomool.api.domain.user.entity.User;

public interface PushNotificationTokenRepository extends JpaRepository<PushNotificationToken, Integer> {
    List<PushNotificationToken> findAllByUser(User user);

    Optional<PushNotificationToken> findByToken(String token);
}
