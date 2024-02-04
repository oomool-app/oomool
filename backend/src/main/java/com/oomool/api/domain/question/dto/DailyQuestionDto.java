package com.oomool.api.domain.question.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class DailyQuestionDto {
    String question;
    int sequence;
    int level;
}
