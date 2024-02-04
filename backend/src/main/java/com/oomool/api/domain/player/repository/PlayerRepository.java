package com.oomool.api.domain.player.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.oomool.api.domain.player.entity.Player;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {

    @Query("SELECT p FROM Player p WHERE p.room.roomUid = :roomUid")
    List<Player> findByRoomUid(String roomUid);

    Player findById(int authorId);

    List<Player> findAll();
}

