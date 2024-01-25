package com.oomool.api.domain.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oomool.api.domain.auth.entity.SocialLogin;

@Repository
public interface OAuthRepository extends JpaRepository<SocialLogin, Integer> {
}
