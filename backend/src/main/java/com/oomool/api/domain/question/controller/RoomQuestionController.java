package com.oomool.api.domain.question.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oomool.api.domain.question.service.RoomQuestionServiceImpl;
import com.oomool.api.global.dto.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/questions")
@RequiredArgsConstructor
@Tag(name = "질문", description = "질문 관련 API를 명세합니다.")
public class RoomQuestionController {

    private final RoomQuestionServiceImpl roomQuestionService;

    @Operation(summary = "방에 대한 질문 생성 기능", description = "문답방에 대한 질문을 생성합니다.")
    @PostMapping("/{roomUID}")
    public ApiResponse<?> createRoomQuestion(@PathVariable("roomUID") String roomUid) {

        // GameRoom을 기준으로 방에 대한 질문을 생성한다.
        roomQuestionService.publishRoomQuestionList(roomUid);

        return ApiResponse.success("문답방의 질문이 생성되었습니다.");
    }

    @Operation(summary = "방 질문 목록에 대한 조회 기능", description = "문답방에 대한 전체 질문을 조회한다.")
    @GetMapping("/{roomUID}")
    public ApiResponse<?> getRoomQuestionList(@PathVariable("roomUID") String roomUid) {
        return ApiResponse.success(
            Map.of(
                "room_uid", roomUid,
                "room_question_list", roomQuestionService.getDailyQuestionList(roomUid))
        );
    }

    @Operation(summary = "데일리 질문 조회 기능", description = "데일리 질문에 대해 조회합니다.")
    @GetMapping("/{roomUID}/daily")
    public ApiResponse<?> getDailyQuestion(@PathVariable("roomUID") String roomUid) {
        // 조회 로직

        return ApiResponse.success(roomQuestionService.getDailyQuestion(roomUid));
    }

}
