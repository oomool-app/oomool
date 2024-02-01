package com.oomool.api.domain.feed.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FeedImageDto {
    private String fileName;
    private String folderName;
    private String url;

    @Builder
    public FeedImageDto(String fileName, String folderName, String url) {
        this.fileName = fileName;
        this.folderName = folderName;
        this.url = url;
    }
}
