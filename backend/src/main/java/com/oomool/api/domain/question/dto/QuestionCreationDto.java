package com.oomool.api.domain.question.dto;

import java.util.Date;

import com.oomool.api.domain.question.entity.QuestionType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QuestionCreationDto {
    Date startDate;
    Date endDate;
    QuestionType questionType;
}
