package com.oomool.api.domain.player.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oomool.api.domain.player.entity.Avatar;

@Repository
public interface AvatarRepository extends JpaRepository<Avatar, Integer> {
    Optional<Avatar> findByUrl(String url);
}
