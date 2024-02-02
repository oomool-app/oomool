package com.oomool.api.domain.question.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RoomQuestionFeedDto {
    private int roomQuestionId; // 방질문 ID
    private int questionId; // 질문 ID
    private int sequence;

    @Builder
    public RoomQuestionFeedDto(int roomQuestionId, int questionId, int sequence) {
        this.roomQuestionId = roomQuestionId;
        this.questionId = questionId;
        this.sequence = sequence;
    }
}
