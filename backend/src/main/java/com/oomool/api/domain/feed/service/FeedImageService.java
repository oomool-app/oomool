package com.oomool.api.domain.feed.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.oomool.api.domain.feed.dto.FeedImageDto;
import com.oomool.api.domain.feed.entity.FeedImage;
import com.oomool.api.domain.feed.repository.FeedImageRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class FeedImageService {

    private final FeedImageRepository feedImageRepository;

    /**
     * 피드 이미지 정보를 모두 반환합니다(파일 명, 폴더 명, url)
     */
    public List<FeedImageDto> getFeedImages(int feedId) {
        List<FeedImage> feedImageList = feedImageRepository.findByFeed_Id(feedId);
        List<FeedImageDto> feedImageDtoList = new ArrayList<>();

        for (FeedImage feedImage : feedImageList) {
            FeedImageDto feedImageDto = FeedImageDto
                .builder()
                .originalName(feedImage.getOriginalName())
                .fileName(feedImage.getSaveName())
                .folderName(feedImage.getSaveFolder())
                .url(feedImage.getUrl())
                .build();

            feedImageDtoList.add(feedImageDto);
        }

        return feedImageDtoList;
    }
}
