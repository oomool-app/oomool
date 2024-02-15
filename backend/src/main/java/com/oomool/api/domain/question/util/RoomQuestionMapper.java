package com.oomool.api.domain.question.util;

import org.springframework.stereotype.Component;

import com.oomool.api.domain.question.dto.DailyQuestionDto;
import com.oomool.api.domain.question.entity.RoomQuestion;

@Component
public class RoomQuestionMapper {

    /**
     * RoomQuesiton -> DailyQuestion
     * */
    public DailyQuestionDto entityToDailyQuestionDto(RoomQuestion roomQuestion) {
        return DailyQuestionDto.builder()
            .question(roomQuestion.getQuestion().getQuestion())
            .dailyDate(roomQuestion.getDate())
            .sequence(roomQuestion.getSequence())
            .level(roomQuestion.getQuestion().getLevel())
            .build();
    }

}
