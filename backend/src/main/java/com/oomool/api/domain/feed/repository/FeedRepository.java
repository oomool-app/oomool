package com.oomool.api.domain.feed.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.oomool.api.domain.feed.entity.Feed;

@Repository
public interface FeedRepository extends JpaRepository<Feed, Integer> {
    @Query("SELECT feed FROM Feed feed WHERE feed.roomQuestion.id = :roomQuestionId")
    List<Feed> findByRoomQuestionId(int roomQuestionId);
}
