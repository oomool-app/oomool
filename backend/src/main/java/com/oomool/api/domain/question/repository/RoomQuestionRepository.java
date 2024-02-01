package com.oomool.api.domain.question.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.oomool.api.domain.question.entity.RoomQuestion;

@Repository
public interface RoomQuestionRepository extends JpaRepository<RoomQuestion, Integer> {
    @Query("SELECT rq AS sequence FROM RoomQuestion rq "
        + "WHERE rq.room.roomUid = :roomUid AND rq.sequence = :sequence")
    RoomQuestion findIdsByRoomUidAndSequence(@Param("roomUid") String roomUid, @Param("sequence") int sequence);
}
