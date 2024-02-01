package com.oomool.api.domain.feed.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 피드 답변했을 때 객체로 넘겨받기 위해 생성한 DTO
 */
@Getter
@Setter
@ToString
public class ReceiveAnswerDto {
    private int roomQuestionId;
    private int authorId;
    private String content;
    private List<MultipartFile> fileList;
}
