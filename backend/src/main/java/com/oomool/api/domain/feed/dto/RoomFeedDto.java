package com.oomool.api.domain.feed.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.oomool.api.domain.player.dto.ManittiDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 문답방의 당일 날 모든 피드 목록
 */
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomFeedDto {
    private int userId; // 작성자 id
    private String content; // 피드 답변 기록
    private int feedId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt; // 답변 생성 일자
    private List<FeedImageDto> feedImageDtoList; // 피드 이미지
    private ManittiDto manittiDto;
}
