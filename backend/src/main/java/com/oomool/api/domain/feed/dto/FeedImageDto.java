package com.oomool.api.domain.feed.dto;

import lombok.AllArgsConstructor;
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
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedImageDto {
    private String originalName;
    private String fileName;
    private String folderName;
    private String url;
}
