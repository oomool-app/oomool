package com.oomool.api.domain.feed.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter // form-data로 받을 때 @Setter로 바인딩하기 때문에 선언해줬습니다.
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReceiveAnswerDto {
    @NotNull
    private int roomQuestionId;
    private int authorId;
    private String content;
    private List<MultipartFile> fileList;
}
