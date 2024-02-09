package com.oomool.api.domain.feed.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 이미지 url 저장 DTO
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImgBbDto {
    private ImgBbImageDto image;
    private ImgBbImageDto thumb;
}
