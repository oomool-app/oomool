package com.oomool.api.domain.feed.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FeedAnswerDto {

    private String content; // 답변 내용
    private List<FeedImageDto> feedImageDtoList; // 이미지 파일
    private String authorId; // 작성자 아이디

    @Builder
    public FeedAnswerDto(String content, List<FeedImageDto> feedImageDtoList, String authorId) {
        this.content = content;
        this.feedImageDtoList = feedImageDtoList;
        this.authorId = authorId;
    }
}
