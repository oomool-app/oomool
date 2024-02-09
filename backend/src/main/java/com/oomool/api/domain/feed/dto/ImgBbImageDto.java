package com.oomool.api.domain.feed.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImgBbImageDto {
    private String filename;
    private String name;
    private String mime;
    private String extension;
    private String url;
}
