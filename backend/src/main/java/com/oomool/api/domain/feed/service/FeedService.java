package com.oomool.api.domain.feed.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.oomool.api.domain.feed.dto.FeedAnswerDto;
import com.oomool.api.domain.feed.dto.ResultRoomFeedDto;

public interface FeedService {
    /**
     * 문답방의 모든 피드를 가져온다
     */
    ResultRoomFeedDto getRoomsFeedList(String roomUid, int sequence);

    /**
     * 질문에 대합 답변 내용 저장(답변 내용, 이미지 파일)
     */
    FeedAnswerDto saveQuestionAnswer(int roomQuestionId, String content,
        List<MultipartFile> fileList, int authorId) throws IOException;

    /**
     * 피드 답변을 수정합니다
     */
    FeedAnswerDto modifyQuestionAnswer(String content, int feedId, List<MultipartFile> fileList) throws
        IOException;

}
