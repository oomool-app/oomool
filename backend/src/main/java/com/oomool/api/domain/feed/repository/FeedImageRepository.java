package com.oomool.api.domain.feed.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.oomool.api.domain.feed.entity.FeedImage;

@Repository
public interface FeedImageRepository extends JpaRepository<FeedImage, Integer> {
    @Query("SELECT fe FROM FeedImage fe where fe.feed.id = :feedId")
    List<FeedImage> findByFeedId(int feedId);
}
