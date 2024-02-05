package com.oomool.api.domain.feed.dto;

import java.util.List;

import com.oomool.api.domain.question.dto.QuestionDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 문답방 피드 전체 조회 Controller 응답 DTO
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResultRoomFeedDto {
    private int roomQuestionId;
    private String date;
    private List<RoomFeedDto> roomFeedDtoList;
    private QuestionDto questionDto;
}

