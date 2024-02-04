package com.oomool.api.domain.feed.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
import com.oomool.api.global.util.ConvertFile;
import com.oomool.api.global.util.CurrentTime;

import jakarta.transaction.Transactional;
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
    private final ConvertFile convertFile;

    /**
     * 문답방의 모든 피드를 가져온다
     */
    public ResultRoomFeedDto getRoomsFeedList(String roomUid, int sequence) {
        // response : 방 질문 ID, 질문 Id, sequence
        RoomQuestionFeedDto roomQuestionFeedDto = roomQuestionService.getRoomQuestion(roomUid, sequence);

        // 방 질문 ID로 모든 피드 목록 가져오기.
        int roomQuestionId = roomQuestionFeedDto.getRoomQuestionId();

        List<Feed> feedAllList = feedRepository.findAll();

        List<Feed> feedList = new ArrayList<>();

        for (Feed feed : feedAllList) {
            int roomQuestionIdCheck = feed.getRoomQuestion().getId();
            // 방 질문 ID가 같은 값을 feedList에 담아준다.
            if (roomQuestionIdCheck == roomQuestionId) {
                feedList.add(feed);
            }
        }

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
                .authorId(authorId)
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

            // 파일을 local에 저장하고 DTO에 담아서 반환해준다.
            FeedImageDto feedImageDto = convertFile.convertFile(file);

            // DB에 파일 저장
            FeedImage registFeedImage = new FeedImage();
            registFeedImage.setSaveFolder(feedImageDto.getFolderName());
            registFeedImage.setOriginalName(feedImageDto.getOriginalName());
            registFeedImage.setSaveName(feedImageDto.getFileName());
            registFeedImage.setFeed(feed);
            registFeedImage.setUrl(feedImageDto.getUrl());
            feedImageRepository.save(registFeedImage);

            // Dto에 파일 저장
            feedImageDtoList.add(feedImageDto);
        }

        FeedAnswerDto feedAnswerDto = FeedAnswerDto
            .builder()
            .feedId(feed.getId())
            .authorId(player.getId())
            .content(content)
            .feedImageDtoList(feedImageDtoList)
            .build();

        return feedAnswerDto;
    }

    /**
     * 드 답변을 수정합니다
     *
     * 파일 delete 할 때 해당 메서드에 @Transaction 붙여줘야함 안그러면
     * 아래 에러 뜸
     * jakarta.persistence.TransactionRequiredException: No EntityManager with actual transaction available for current thread - cannot reliably process 'remove' call
     * 해석하면 EntityManager가 없어서 안정적인 제거를 할 수 없다는 얘기임.
     */
    @Transactional
    public FeedAnswerDto modifyQuestionAnswer(String content, int feedId, List<MultipartFile> fileList) throws
        IOException {

        // 답변 수정
        Feed feed = feedRepository.findById(feedId);
        // EM에서 변경감지 처리해서 자동 수정
        feed.setContent(content);

        // 기존 DB에 있던 파일을 삭제한다.
        feedImageRepository.deleteByFeedId(feedId);

        List<MultipartFile> newFileList = fileList;

        List<FeedImageDto> feedImageDtoList = new ArrayList<>();


        // 새로 입력 받은 이미지 DB에 저장
        for (int i = 0; i < newFileList.size(); i++) {
            MultipartFile file = newFileList.get(i);

            FeedImageDto feedImageDto = convertFile.convertFile(file);

            FeedImage registFeedImage = new FeedImage();

            registFeedImage.setFeed(feed);
            registFeedImage.setOriginalName(feedImageDto.getOriginalName());
            registFeedImage.setSaveName(feedImageDto.getFileName());
            registFeedImage.setSaveFolder(feedImageDto.getFolderName());
            registFeedImage.setUrl(feedImageDto.getUrl());

            // save 안하면 변경 감지 안되던데 이유가..? 그래서 save 처리해줌!
            feedImageRepository.save(registFeedImage);
            feedImageDtoList.add(feedImageDto);
        }

        FeedAnswerDto feedAnswerDto = FeedAnswerDto
            .builder()
            .feedId(feedId)
            .feedImageDtoList(feedImageDtoList)
            .content(content)
            .authorId(feed.getAuthor().getId())
            .build();

        return feedAnswerDto;
    }

}
