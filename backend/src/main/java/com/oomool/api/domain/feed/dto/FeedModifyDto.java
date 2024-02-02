package com.oomool.api.domain.feed.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FeedModifyDto {
    private String content;
    private List<MultipartFile> fileList;
    private int feedId;
}
