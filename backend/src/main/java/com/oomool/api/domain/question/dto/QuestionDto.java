package com.oomool.api.domain.question.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class QuestionDto {
    private String question;
    private int level;

    @Builder
    public QuestionDto(String question, int level) {
        this.question = question;
        this.level = level;
    }
}
