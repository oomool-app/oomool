package com.oomool.api.domain.feed.dto;

import java.util.List;

import com.oomool.api.domain.question.dto.QuestionDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResultRoomFeedDto {
    private String date;
    private List<RoomFeedDto> roomFeedDtoList;
    private QuestionDto questionDto;

    @Builder
    public ResultRoomFeedDto(String date, List<RoomFeedDto> roomFeedDtoList, QuestionDto questionDto) {
        this.date = date;
        this.roomFeedDtoList = roomFeedDtoList;
        this.questionDto = questionDto;
    }
}
