package com.oomool.api.domain.room.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.oomool.api.domain.question.entity.QuestionType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SettingOptionDto {
    private String title;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date endDate;
    private QuestionType questionType;
    private int maxMember;
}
