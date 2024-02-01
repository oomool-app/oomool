package com.oomool.api.domain.feed.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oomool.api.domain.feed.dto.ResultRoomFeedDto;
import com.oomool.api.domain.feed.service.FeedService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequiredArgsConstructor
@Tag(name = "피드", description = "피드 API를 명세합니다.")
@RequestMapping("/feeds")
public class FeedController {

    private final FeedService feedService;

    /**
     * 문답방 모든 피드를 반환한다.
     */
    @Operation(summary = "문답방 질문의 모든 피드", description = "문답방 질문의 모든 피드를 반환합니다.")
    @GetMapping("/{roomUid}/{sequence}")
    public ResponseEntity<ResultRoomFeedDto> getRoomsFeed(@PathVariable("roomUid") String roomUid,
        @PathVariable("sequence") int sequence) {

        ResultRoomFeedDto resultRoomFeedDto = feedService.getRoomsFeedList(roomUid, sequence);

        return new ResponseEntity<>(resultRoomFeedDto, HttpStatus.OK);
    }

}
