package com.oomool.api.domain.room.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oomool.api.domain.room.entity.GameRoom;

@Repository
public interface GameRoomRepository extends JpaRepository<GameRoom, Integer> {

    // roomUid에 대한 조회 메서드
    Optional<GameRoom> findByRoomUid(String roomUid);

}
