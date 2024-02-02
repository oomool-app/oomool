package com.oomool.api.domain.feed.dto;

import java.util.List;

import com.oomool.api.domain.player.dto.ManittiDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 문답방의 당일 날 모든 피드 목록
 */
@Getter
@Setter
@ToString
public class RoomFeedDto {
    private int authorId; // 작성자 id
    private String content; // 피드 답변 기록
    private String createAt; // 답변 생성 일자
    private List<FeedImageDto> feedImageDtoList; // 피드 이미지
    private ManittiDto manittiDto;

    @Builder
    public RoomFeedDto(String content, String createAt,
        List<FeedImageDto> feedImageDtoList, ManittiDto manittiDto, int authorId) {
        this.authorId = authorId;
        this.content = content;
        this.createAt = createAt;
        this.feedImageDtoList = feedImageDtoList;
        this.manittiDto = manittiDto;
    }
}
