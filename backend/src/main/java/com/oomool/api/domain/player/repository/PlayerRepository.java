package com.oomool.api.domain.player.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oomool.api.domain.player.entity.Player;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {
}

