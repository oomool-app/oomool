package com.oomool.api.domain.feed.service;

import java.util.List;

import com.oomool.api.domain.feed.dto.FeedImageDto;

public interface FeedImageService {
    /**
     * 피드 이미지 정보를 모두 반환합니다(파일 명, 폴더 명, url)
     */
    List<FeedImageDto> getFeedImages(int feedId);
}
