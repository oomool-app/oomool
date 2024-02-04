package com.oomool.api.domain.feed.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 피드 답변에서 등록한 이미지를 저장하고 feedAnswerDto에 담기위해 사용되는 DTO
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class FeedImageDto {
    private String originalName;
    private String fileName;
    private String folderName;
    private String url;

    @Builder
    public FeedImageDto(String originalName, String fileName, String folderName, String url) {
        this.originalName = originalName;
        this.fileName = fileName;
        this.folderName = folderName;
        this.url = url;
    }
}
