package com.oomool.api.domain.feed.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oomool.api.domain.feed.dto.FeedAnswerDto;
import com.oomool.api.domain.feed.dto.FeedModifyDto;
import com.oomool.api.domain.feed.dto.ReceiveAnswerDto;
import com.oomool.api.domain.feed.dto.ResultRoomFeedDto;
import com.oomool.api.domain.feed.service.FeedService;
import com.oomool.api.domain.question.service.RoomQuestionService;

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
    private final RoomQuestionService roomQuestionService;

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

    /**
     * 피드 질문 답변 등록
     */
    @Operation(summary = "오늘 질문 답변", description = "사용자가 오늘 질문에 대해 답변합니다.")
    @PostMapping("/daily")
    public ResponseEntity<FeedAnswerDto> saveQuestionAnswer(ReceiveAnswerDto receiveAnswerDto) throws IOException {

        // 피드 답변 등록하기
        FeedAnswerDto feedAnswerDto = feedService.saveQuestionAnswer(receiveAnswerDto.getRoomQuestionId(),
            receiveAnswerDto.getContent(), receiveAnswerDto.getFileList(),
            receiveAnswerDto.getAuthorId());
        return new ResponseEntity<>(feedAnswerDto, HttpStatus.CREATED);
    }

    /**
     * 피드 질문 답변 수정
     */
    @Operation(summary = "피드 질문 답변을 수정", description = "등록한 질문을 수정합니다.")
    @PatchMapping("/daily")
    public ResponseEntity<FeedAnswerDto> modifyQuestionAnswer(FeedModifyDto feedModifyDto) throws IOException {
        FeedAnswerDto feedAnswerDto = feedService.modifyQuestionAnswer(feedModifyDto);
        return new ResponseEntity<>(feedAnswerDto, HttpStatus.CREATED);
    }
}
