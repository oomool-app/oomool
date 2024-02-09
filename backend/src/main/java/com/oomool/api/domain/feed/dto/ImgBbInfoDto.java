package com.oomool.api.domain.feed.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * response 응답 데이터를 담는 dto
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImgBbInfoDto {
    private ImgBbDto data;
    private boolean success;
    private int status;
}
