package com.oomool.api.domain.feed.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oomool.api.domain.feed.entity.FeedImage;

@Repository
public interface FeedImageRepository extends JpaRepository<FeedImage, Integer> {
    List<FeedImage> findByFeed_Id(int feedId);

    void deleteByFeedId(int feedId);
}
