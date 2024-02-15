package com.oomool.api.domain.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oomool.api.domain.auth.entity.SocialLogin;

@Repository
public interface OAuthRepository extends JpaRepository<SocialLogin, Integer> {
    Optional<SocialLogin> findByUserId(int userId);
}
