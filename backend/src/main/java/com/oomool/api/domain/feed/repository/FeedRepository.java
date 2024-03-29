package com.oomool.api.domain.feed.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oomool.api.domain.feed.entity.Feed;

@Repository
public interface FeedRepository extends JpaRepository<Feed, Integer> {
    Feed findById(int feedId);

    List<Feed> findByRoomQuestionId(int roomQuestionId);

    Feed findByRoomQuestionIdAndAuthorUserId(int roomQuestionId, int userId);
}
