package com.oomool.api.domain.feed.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 피드 답변 Controller 반환 DTO
 */
@Getter
@Setter
@ToString
public class FeedAnswerDto {
    private int feedId; // 답변을 등록한 해당 피드 id
    private String content; // 답변 내용
    private List<FeedImageDto> feedImageDtoList; // 이미지 파일
    private int authorId; // 작성자 아이디

    @Builder
    public FeedAnswerDto(int feedId, String content, List<FeedImageDto> feedImageDtoList, int authorId) {
        this.feedId = feedId;
        this.content = content;
        this.feedImageDtoList = feedImageDtoList;
        this.authorId = authorId;
    }
}
