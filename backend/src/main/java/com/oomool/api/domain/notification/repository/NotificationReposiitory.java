package com.oomool.api.domain.notification.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oomool.api.domain.notification.entity.Notification;

@Repository
public interface NotificationReposiitory extends JpaRepository<Notification, Integer> {
    List<Notification> findByUserId(int userId);
}
