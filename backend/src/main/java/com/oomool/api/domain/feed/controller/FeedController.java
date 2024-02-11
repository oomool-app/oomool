package com.oomool.api.domain.feed.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.oomool.api.domain.feed.dto.FeedAnswerDto;
import com.oomool.api.domain.feed.dto.ImageResponseDto;
import com.oomool.api.domain.feed.dto.ResultManittoDto;
import com.oomool.api.domain.feed.dto.ResultRoomFeedDto;
import com.oomool.api.domain.feed.service.FeedService;
import com.oomool.api.domain.feed.service.ImgBbImageUploadApi;
import com.oomool.api.global.util.ResponseHandler;

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
    private final ImgBbImageUploadApi imgBbImageUploadApi;

    /**
     * 문답방 모든 피드를 반환한다.
     */
    @Operation(summary = "문답방 질문의 모든 피드", description = "문답방 질문의 모든 피드를 반환합니다.")
    @GetMapping("/{roomUid}/{sequence}")
    public ResponseEntity<?> getRoomsFeed(@PathVariable("roomUid") String roomUid,
        @PathVariable("sequence") int sequence) {

        ResultRoomFeedDto resultRoomFeedDto = feedService.getRoomsFeedList(roomUid, sequence);

        return ResponseHandler.generateResponse(HttpStatus.OK, resultRoomFeedDto);
    }

    /**
     * 피드 질문 답변 등록
     */
    @Operation(summary = "오늘 질문 답변", description = "사용자가 오늘 질문에 대해 답변합니다.")
    @PostMapping(value = "/daily", produces = "application/json", consumes = "multipart/form-data")
    public ResponseEntity<?> saveQuestionAnswer(@RequestParam("content") String content,
        @RequestParam("author_id") String authorId,
        @RequestParam("room_question_id") String roomQuestionId,
        @RequestPart(value = "file_list", required = false) List<MultipartFile> fileList) throws IOException {

        ArrayList<String> imageUrlList = getImageUrlList(fileList);

        // 피드 답변 등록하기
        FeedAnswerDto feedAnswerDto = feedService.saveQuestionAnswer(Integer.parseInt(roomQuestionId),
            content, fileList, Integer.parseInt(authorId), imageUrlList);

        return ResponseHandler.generateResponse(HttpStatus.CREATED, feedAnswerDto);
    }

    /**
     * 피드 질문 답변 수정
     */
    @Operation(summary = "피드 질문 답변을 수정", description = "등록한 질문을 수정합니다.")
    @PatchMapping(value = "/daily", produces = "application/json", consumes = "multipart/form-data")
    public ResponseEntity<?> modifyQuestionAnswer(@RequestParam("content") String content,
        @RequestParam("feed_id") String feedId,
        @RequestParam(value = "url", required = false) List<String> urlList,
        @RequestPart(value = "file_list", required = false) List<MultipartFile> fileList) throws IOException {

        ArrayList<String> imageUrlList = getImageUrlList(fileList);

        FeedAnswerDto feedAnswerDto = feedService.modifyQuestionAnswer(content, Integer.parseInt(feedId), fileList,
            imageUrlList, urlList);
        return ResponseHandler.generateResponse(HttpStatus.OK, feedAnswerDto);
    }

    /**
     * @return 나의 마니또의 모든 피드 결과
     */
    @Operation(summary = "나의 마니또 모든 피드 조회", description = "나의 마니또의 모든 피드 내역을 조회합니다.")
    @GetMapping("/{roomUid}/{userId}/result")
    public ResponseEntity<?> getResultManittoFeed(@PathVariable("roomUid") String roomUid,
        @PathVariable("userId") int userId) {

        ResultManittoDto resultManittoDto = feedService.getManittoFeed(roomUid, userId);

        return ResponseHandler.generateResponse(HttpStatus.OK, resultManittoDto);
    }

    /**
     * 받아온 파일들을 기반으로 이미지 url을 만들고 반환하는 메서드
     */
    private ArrayList<String> getImageUrlList(List<MultipartFile> fileList) throws IOException {

        ArrayList<String> imageUrlList = new ArrayList<>();

        if (fileList == null) {
            return imageUrlList;
        }

        for (MultipartFile file : fileList) {
            ImageResponseDto imageResponseDto = imgBbImageUploadApi.uploadImage(file);
            String imageUrl = imageResponseDto.getImage();
            imageUrlList.add(imageUrl);
        }

        return imageUrlList;
    }


}


