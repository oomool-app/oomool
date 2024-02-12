package com.oomool.api.domain.feed.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.oomool.api.domain.feed.dto.FeedAnswerDto;
import com.oomool.api.domain.feed.dto.FeedImageDto;
import com.oomool.api.domain.feed.dto.ResultManittoDto;
import com.oomool.api.domain.feed.dto.ResultRoomFeedDto;
import com.oomool.api.domain.feed.dto.RoomFeedDto;
import com.oomool.api.domain.feed.dto.RoomManittoDto;
import com.oomool.api.domain.feed.entity.Feed;
import com.oomool.api.domain.feed.entity.FeedImage;
import com.oomool.api.domain.feed.repository.FeedImageRepository;
import com.oomool.api.domain.feed.repository.FeedRepository;
import com.oomool.api.domain.notification.constant.NotificationType;
import com.oomool.api.domain.notification.dto.NotificationSaveRequestDto;
import com.oomool.api.domain.notification.dto.PushNotificationDto;
import com.oomool.api.domain.notification.service.NotificationService;
import com.oomool.api.domain.notification.service.PushNotificationService;
import com.oomool.api.domain.player.dto.ManittiDto;
import com.oomool.api.domain.player.dto.ManittoDto;
import com.oomool.api.domain.player.entity.Player;
import com.oomool.api.domain.player.repository.PlayerRepository;
import com.oomool.api.domain.player.service.PlayerService;
import com.oomool.api.domain.question.dto.QuestionDto;
import com.oomool.api.domain.question.dto.RoomQuestionFeedDto;
import com.oomool.api.domain.question.entity.RoomQuestion;
import com.oomool.api.domain.question.repository.RoomQuestionReposiotry;
import com.oomool.api.domain.question.service.QuestionService;
import com.oomool.api.domain.question.service.RoomQuestionService;
import com.oomool.api.domain.room.entity.GameRoom;
import com.oomool.api.global.util.ConvertFile;
import com.oomool.api.global.util.CurrentTime;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * 트랜잭션에서 여러 서비스를 의존하는 것은
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class FeedServiceImpl implements FeedService {
    private final RoomQuestionService roomQuestionService;
    private final RoomQuestionReposiotry roomQuestionReposiotry;
    private final FeedRepository feedRepository;
    private final FeedImageService feedImageService;
    private final QuestionService questionService;
    private final PlayerService playerService;
    private final PlayerRepository playerRepository;
    private final FeedImageRepository feedImageRepository;
    private final ConvertFile convertFile;
    private final NotificationService notificationService;
    private final PushNotificationService pushNotificationService;

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
            // 피드 id에 해당하는 이미지 모두 가져온다
            int feedId = feed.getId();
            List<FeedImageDto> feedImageDtoList = feedImageService.getFeedImages(feedId);

            // 작성자 ID를 가져와서 마니띠를 찾는다.
            int authorId = feed.getAuthor().getUser().getId();

            ManittiDto manittiDto = playerService.getManittiInfo(roomUid, authorId);

            RoomFeedDto roomFeedDto = RoomFeedDto
                .builder()
                .feedId(feedId)
                .userId(authorId)
                .content(feed.getContent()) // 답변 내용
                .createdAt(feed.getCreatedAt()) // 생성 일자
                .feedImageDtoList(feedImageDtoList) // 이미지 파일 리스트
                .manittiDto(manittiDto) // 마니띠 정보
                .build();

            roomFeedDtoList.add(roomFeedDto);
        }

        // 질문 ID로 질문 내용과, 레벨 가져오기
        int questionId = roomQuestionFeedDto.getQuestionId();
        QuestionDto questionDto = questionService.getQuestion(questionId);

        return ResultRoomFeedDto
            .builder()
            .roomQuestionId(roomQuestionId) // 방 질문 ID
            .date(CurrentTime.getCurrentTime()) // 현재 날짜
            .roomFeedDtoList(roomFeedDtoList) // 피드 정보(생성 일자, 답변 내용, 마니띠 정보, 이미지 파일)
            .questionDto(questionDto) // 질문 내용, 레벨
            .build();
    }

    /**
     * 1. 질문에 대합 답변 내용 저장(답변 내용, 이미지 파일)
     * 2. 답변한 내용을 Notification에 저장하고 마니띠에게 알림 전달.
     */
    public FeedAnswerDto saveQuestionAnswer(int roomQuestionId, String content,
        List<MultipartFile> fileList, int authorId, ArrayList<String> imageUrlList) throws IOException {

        RoomQuestion roomQuestion = roomQuestionService.getRoomQuestionById(roomQuestionId);

        String roomUid = roomQuestion.getRoom().getRoomUid();

        Player player = playerService.getPlayerInfo(roomUid, authorId);

        GameRoom gameRoom = roomQuestion.getRoom();

        int manittiId = player.getManittiId();

        // 피드 저장
        Feed registFeed = Feed.builder()
            .roomQuestion(roomQuestion)
            .author(player)
            .content(content)
            .build();

        Feed feed = feedRepository.save(registFeed);

        List<FeedImageDto> feedImageDtoList = new ArrayList<>();

        if (fileList != null) {
            // 피드 이미지 저장
            for (int i = 0; i < fileList.size(); i++) {
                MultipartFile file = fileList.get(i);
                String imageUrl = imageUrlList.get(i);

                // 파일을 local에 저장하고 DTO에 담아서 반환해준다.
                FeedImageDto feedImageDto = convertFile.convertFile(file, imageUrl);

                saveFeedImage(feed, feedImageDto);

                // Dto에 파일 저장
                feedImageDtoList.add(feedImageDto);
            }
        }

        // Notification에 답변 저장
        // Notification에는 피드 답변 내용을 저장
        NotificationSaveRequestDto notificationSaveRequestDto = new NotificationSaveRequestDto(manittiId,
            NotificationType.SYSTEM, gameRoom.getRoomUid(), gameRoom.getTitle(), content);

        notificationService.saveNotification(notificationSaveRequestDto);

        // 마니띠에게 push 알림 보내기
        // 알림에는 내 마니또가 답변을 등록했어요!를 저장 
        PushNotificationDto pushNotificationDto = new PushNotificationDto(manittiId, gameRoom.getTitle(),
            "내 마니또가 답변을 등록했어요!");

        // send notification
        try {
            pushNotificationService.sendPushNotificationByUser(pushNotificationDto);
        } catch (EntityNotFoundException e) {
            log.info("토큰이 존재하지 않습니다!!");
        }

        return FeedAnswerDto
            .builder()
            .feedId(feed.getId())
            .content(content)
            .feedImageDtoList(feedImageDtoList)
            .build();
    }

    /**
     * 피드 답변을 수정합니다
     * <p>
     * 파일 delete 할 때 해당 메서드에 @Transaction 붙여줘야함 안그러면
     * 아래 에러 뜸
     * jakarta.persistence.TransactionRequiredException:
     * No EntityManager with actual transaction available for current thread - cannot reliably process 'remove' call
     * 해석하면 EntityManager가 없어서 안정적인 제거를 할 수 없다는 얘기임.
     */
    @Transactional
    public FeedAnswerDto modifyQuestionAnswer(String content, int feedId,
        List<MultipartFile> fileList, ArrayList<String> imageUrlList, List<String> urlList) throws
        IOException {

        // 답변 수정
        Feed feed = feedRepository.findById(feedId);
        // EM에서 변경감지 처리해서 자동 수정
        feed.update(content);

        // 기존 DB에 있던 파일을 삭제한다.
        feedImageRepository.deleteByFeedId(feedId);

        List<FeedImageDto> feedImageDtoList = new ArrayList<>();

        if (fileList != null) {
            // 새로 입력 받은 이미지 DB에 저장
            for (int i = 0; i < fileList.size(); i++) {
                MultipartFile file = fileList.get(i);
                String imageUrl = imageUrlList.get(i);

                FeedImageDto feedImageDto = convertFile.convertFile(file, imageUrl);

                saveFeedImage(feed, feedImageDto);

                feedImageDtoList.add(feedImageDto);
            }
        }
        /**
         * 기존에 있던 url에 변경 사항이 없다면 다시 DB에 저장
         */
        if (urlList != null) {
            for (String url : urlList) {
                FeedImage feedImage = FeedImage
                    .builder()
                    .url(url)
                    .feed(feed)
                    .build();

                FeedImageDto feedImageDto = FeedImageDto
                    .builder()
                    .url(url)
                    .build();

                feedImageRepository.save(feedImage);
                feedImageDtoList.add(feedImageDto);
            }
        }

        return FeedAnswerDto
            .builder()
            .feedId(feedId)
            .content(content)
            .feedImageDtoList(feedImageDtoList)
            .build();
    }

    /**
     * @return 마니또의 모든 피드 결과
     */
    @Override
    public ResultManittoDto getManittoFeed(String roomUid, int userId) {
        List<RoomQuestion> roomQuestionList = roomQuestionReposiotry.findByRoomRoomUid(roomUid);

        // 마니또 정보 가져오기
        Player player = playerRepository.findByRoomRoomUidAndManittiId(roomUid, userId);

        int manittoId = player.getUser().getId();

        ManittoDto manittoDto = ManittoDto
            .builder()
            .nickname(player.getNickname())
            .avatarColor(player.getAvatarColor())
            .url(player.getAvatar().getUrl())
            .build();
        List<RoomManittoDto> resultManittoDtoList = new ArrayList<>();

        for (int i = 0; i < roomQuestionList.size(); i++) {
            RoomQuestion roomQuestion = roomQuestionList.get(i);

            // 질문 level, 질문 내용
            QuestionDto questionDto = QuestionDto
                .builder()
                .question(roomQuestion.getQuestion().getQuestion())
                .level(roomQuestion.getQuestion().getLevel())
                .build();

            // 플레이어 피드 정보 가져오기
            Feed feed = feedRepository.findByRoomQuestionIdAndAuthorUserId(roomQuestion.getId(), manittoId);

            RoomManittoDto roomManittoDto = new RoomManittoDto();

            /**
             * 마니또가 피드를 작성하지 않았을 때 질문 정보만 넘긴다.
             */
            if (feed == null) {
                roomManittoDto = RoomManittoDto
                    .builder()
                    .roomQuestionId(roomQuestion.getId())
                    .questionDto(questionDto)
                    .build();

                resultManittoDtoList.add(roomManittoDto);
                continue;
            }

            // 피드 이미지 가져오기
            List<FeedImageDto> feedImageDtoList = feedImageService.getFeedImages(feed.getId());

            // 작성한 피드에 대한 정보(질문, 피드, 피드 이미지)
            roomManittoDto = RoomManittoDto
                .builder()
                .roomQuestionId(roomQuestion.getId())
                .userId(manittoId)
                .content(feed.getContent())
                .feedId(feed.getId())
                .createdAt(feed.getCreatedAt())
                .feedImageDtoList(feedImageDtoList)
                .questionDto(questionDto)
                .build();

            resultManittoDtoList.add(roomManittoDto);
        }

        // 마니또 정보 + 피드 정보
        ResultManittoDto resultManittoDto = ResultManittoDto
            .builder()
            .manittoDto(manittoDto)
            .resultManittoList(resultManittoDtoList)
            .build();

        return resultManittoDto;
    }

    /**
     * DB 이미지 저장 메서드
     */
    public void saveFeedImage(Feed feed, FeedImageDto feedImageDto) {
        // DB에 파일 저장
        FeedImage registFeedImage = FeedImage
            .builder()
            .saveFolder(feedImageDto.getFolderName())
            .originalName(feedImageDto.getOriginalName())
            .saveName(feedImageDto.getFileName())
            .feed(feed)
            .url(feedImageDto.getUrl())
            .build();

        feedImageRepository.save(registFeedImage);
    }
}

