package com.oomool.api.domain.feed.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 응답으로 반환해줄 DTO
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageResponseDto {
    private String image; // 일반 이미지 크기
    private String thumb; // 작은 이미지
}
