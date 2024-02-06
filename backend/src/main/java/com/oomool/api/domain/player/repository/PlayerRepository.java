package com.oomool.api.domain.player.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oomool.api.domain.player.entity.Player;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {
    Player findById(int authorId);

    Player findByRoomRoomUidAndUserId(String roomUid, int userId);

    Player findByRoomRoomUidAndManittiId(String roomUid, int userId);

    Player findByUserId(int userId);
}

