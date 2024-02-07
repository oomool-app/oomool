package com.oomool.api.domain.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oomool.api.domain.user.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmailAndProvider(String email, String registrationId);

    @EntityGraph(attributePaths = {"socialLoginList"})
    Optional<User> findByEmail(String email);
}
