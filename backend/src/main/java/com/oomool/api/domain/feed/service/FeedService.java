package com.oomool.api.domain.feed.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.oomool.api.domain.feed.dto.FeedImageDto;
import com.oomool.api.domain.feed.dto.ResultRoomFeedDto;
import com.oomool.api.domain.feed.dto.RoomFeedDto;
import com.oomool.api.domain.feed.entity.Feed;
import com.oomool.api.domain.feed.repository.FeedRepository;
import com.oomool.api.domain.player.dto.ManittiDto;
import com.oomool.api.domain.player.service.PlayerService;
import com.oomool.api.domain.question.dto.QuestionDto;
import com.oomool.api.domain.question.dto.RoomQuestionFeedDto;
import com.oomool.api.domain.question.service.QuestionService;
import com.oomool.api.domain.question.service.RoomQuestionService;

import lombok.RequiredArgsConstructor;

/**
 * 트랜잭션에서 여러 서비스를 의존하는 것은
 */
@Service
@RequiredArgsConstructor
public class FeedService {

    private final RoomQuestionService roomQuestionService;
    private final FeedRepository feedRepository;
    private final FeedImageService feedImageService;
    private final QuestionService questionService;
    private final PlayerService playerService;

    /**
     * 문답방의 모든 피드를 가져온다
     */
    public ResultRoomFeedDto getRoomsFeedList(String roomUid, int sequence) {
        // response : 방 질문 ID, 질문 Id, sequence
        RoomQuestionFeedDto roomQuestionFeedDto = roomQuestionService.getRoomQuestion(roomUid, sequence);

        // 방 질문 ID로 모든 피드 목록 가져오기.
        int roomQuestionId = roomQuestionFeedDto.getRoomQuestionId();

        List<Feed> feedList = feedRepository.findByRoomQuestionId(roomQuestionId);

        List<RoomFeedDto> roomFeedDtoList = new ArrayList<>();

        for (Feed feed : feedList) {
            // 피드 id에 해당하는 이미지 모두 가져와서 저장
            int feedId = feed.getId();
            List<FeedImageDto> feedImageDtoList = feedImageService.getFeedImages(feedId);

            // 작성자 ID를 가져와서 마니띠를 찾는다.
            int authorId = feed.getAuthor().getId();
            ManittiDto manittiDto = playerService.getManittiInfo(authorId);

            RoomFeedDto roomFeedDto = RoomFeedDto
                .builder()
                .content(feed.getContent()) // 답변 내용
                .createAt(feed.getCreateAt().toString()) // 피드 생성 날짜
                .feedImageDtoList(feedImageDtoList) // 이미지 파일 리스트
                .manittiDto(manittiDto) // 마니띠 정보
                .build();

            roomFeedDtoList.add(roomFeedDto);
        }

        // 질문 ID로 질문 내용과, 레벨 가져오기
        int questionId = roomQuestionFeedDto.getQuestionId();
        QuestionDto questionDto = questionService.getQuestion(questionId);

        // 현재 시간을 가져옵니다.
        LocalDateTime currentTime = LocalDateTime.now();

        // 날짜와 시간을 원하는 형식으로 포맷합니다.
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTime = currentTime.format(formatter);

        ResultRoomFeedDto resultRoomFeedDto = ResultRoomFeedDto
            .builder()
            .date(formattedTime) // 현재 날짜
            .roomFeedDtoList(roomFeedDtoList) // 피드 정보(생성 일자, 답변 내용, 마니띠 정보, 이미지 파일)
            .questionDto(questionDto) // 질문 내용, 레벨
            .build();

        return resultRoomFeedDto;
    }

}
