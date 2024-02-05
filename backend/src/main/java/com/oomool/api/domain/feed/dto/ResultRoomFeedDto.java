package com.oomool.api.domain.feed.dto;

import java.util.List;

import com.oomool.api.domain.question.dto.QuestionDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 문답방 피드 전체 조회 Controller 응답 DTO
 */
@Getter
@Setter
@ToString
public class ResultRoomFeedDto {
    private int roomQuestionId;
    private String date;
    private List<RoomFeedDto> roomFeedDtoList;
    private QuestionDto questionDto;

    @Builder
    public ResultRoomFeedDto(int roomQuestionId, String date, List<RoomFeedDto> roomFeedDtoList,
        QuestionDto questionDto) {
        this.roomQuestionId = roomQuestionId;
        this.date = date;
        this.roomFeedDtoList = roomFeedDtoList;
        this.questionDto = questionDto;
    }
}

