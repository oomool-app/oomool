package com.oomool.api.domain.feed.service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.oomool.api.domain.feed.dto.FeedAnswerDto;
import com.oomool.api.domain.feed.dto.FeedImageDto;
import com.oomool.api.domain.feed.dto.ResultRoomFeedDto;
import com.oomool.api.domain.feed.dto.RoomFeedDto;
import com.oomool.api.domain.feed.entity.Feed;
import com.oomool.api.domain.feed.entity.FeedImage;
import com.oomool.api.domain.feed.repository.FeedImageRepository;
import com.oomool.api.domain.feed.repository.FeedRepository;
import com.oomool.api.domain.player.dto.ManittiDto;
import com.oomool.api.domain.player.entity.Player;
import com.oomool.api.domain.player.service.PlayerService;
import com.oomool.api.domain.question.dto.QuestionDto;
import com.oomool.api.domain.question.dto.RoomQuestionFeedDto;
import com.oomool.api.domain.question.entity.RoomQuestion;
import com.oomool.api.domain.question.service.QuestionService;
import com.oomool.api.domain.question.service.RoomQuestionService;
import com.oomool.api.global.util.CurrentTime;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * 트랜잭션에서 여러 서비스를 의존하는 것은
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class FeedService {
    private final RoomQuestionService roomQuestionService;
    private final FeedRepository feedRepository;
    private final FeedImageService feedImageService;
    private final QuestionService questionService;
    private final PlayerService playerService;
    private final FeedImageRepository feedImageRepository;
    @Value("${file.path}")
    private String uploadPath;

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

        ResultRoomFeedDto resultRoomFeedDto = ResultRoomFeedDto
            .builder()
            .roomQuestionId(roomQuestionId) // 방 질문 ID
            .date(CurrentTime.getCurrentTime()) // 현재 날짜
            .roomFeedDtoList(roomFeedDtoList) // 피드 정보(생성 일자, 답변 내용, 마니띠 정보, 이미지 파일)
            .questionDto(questionDto) // 질문 내용, 레벨
            .build();

        return resultRoomFeedDto;
    }

    /**
     * 질문에 대합 답변 내용 저장(답변 내용, 이미지 파일)
     */
    public FeedAnswerDto saveQuestionAnswer(int roomQuestionId, String content,
        List<MultipartFile> fileList, int authorId) throws IOException {

        RoomQuestion roomQuestion = roomQuestionService.getRoomQuestionById(roomQuestionId);
        Player player = playerService.getPlayerInfo(authorId);

        // 피드 저장
        Feed registFeed = new Feed();

        registFeed.setRoomQuestion(roomQuestion);
        registFeed.setAuthor(player);
        registFeed.setContent(content);
        registFeed.setCreateAt(LocalDateTime.now());

        Feed feed = feedRepository.save(registFeed);

        List<FeedImageDto> feedImageDtoList = new ArrayList<>();

        // 피드 이미지 저장
        for (MultipartFile file : fileList) {
            // DB에 파일 저장
            String today = new SimpleDateFormat("yyMMdd").format(new Date());
            File folder = new File(uploadPath);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            String originalFileName = file.getOriginalFilename();

            // DB에 저장
            FeedImage registFeedImage = new FeedImage();

            if (!originalFileName.isEmpty()) {
                String saveFileName = UUID.randomUUID().toString()
                    + originalFileName.substring(originalFileName.lastIndexOf('.'));
                registFeedImage.setSaveFolder(today);
                registFeedImage.setOriginalName(originalFileName);
                registFeedImage.setSaveName(saveFileName);
                file.transferTo(new File(folder, saveFileName));

                // DTO에 파일 저장
                FeedImageDto feedImageDto = FeedImageDto
                    .builder()
                    .originalName(originalFileName)
                    .fileName(saveFileName)
                    .folderName(today)
                    .url("임의의 url")
                    .build();

                feedImageDtoList.add(feedImageDto);
            }
            registFeedImage.setFeed(feed);
            registFeedImage.setUrl("임의의 url");

            feedImageRepository.save(registFeedImage);
        }

        FeedAnswerDto feedAnswerDto = FeedAnswerDto
            .builder()
            .authorId(player.getId())
            .content(content)
            .feedImageDtoList(feedImageDtoList)
            .build();

        return feedAnswerDto;
    }
}
