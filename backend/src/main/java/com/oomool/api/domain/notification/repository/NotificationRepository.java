package com.oomool.api.domain.notification.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oomool.api.domain.notification.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    List<Notification> findAllByUserId(int userId);

    boolean existsByUserIdAndReadAtIsNull(int userId);
}
