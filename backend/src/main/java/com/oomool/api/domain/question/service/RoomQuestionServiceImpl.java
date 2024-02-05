package com.oomool.api.domain.question.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oomool.api.domain.question.dto.DailyQuestionDto;
import com.oomool.api.domain.question.entity.Question;
import com.oomool.api.domain.question.entity.RoomQuestion;
import com.oomool.api.domain.question.repository.QuestionRepository;
import com.oomool.api.domain.question.repository.RoomQuestionReposiotry;
import com.oomool.api.domain.question.util.RoomQuestionMapper;
import com.oomool.api.domain.question.util.RoomQuestionUtil;
import com.oomool.api.domain.room.entity.GameRoom;
import com.oomool.api.domain.room.service.GameRoomServiceImpl;
import com.oomool.api.global.util.CustomDateUtil;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RoomQuestionServiceImpl implements RoomQuestionService {

    private final RoomQuestionReposiotry roomQuestionReposiotry;
    private final QuestionRepository questionRepository;
    private final RoomQuestionMapper roomQuestionMapper;

    // GameRoomService
    private final GameRoomServiceImpl gameRoomService;
    // 문답방의 질문 생성기
    private final RoomQuestionUtil roomQuestionUtil;

    @Override
    public void publishRoomQuestionList(String roomUid) {

        GameRoom gameRoom = gameRoomService.getGameRoom(roomUid);
        // 질문 생성에 필요한 조건을 구한다.
        // 1. 몇일 동안 진행될 것인지
        // 2. 질문의 깊이는 어느정도인지
        int dateInterval = CustomDateUtil.getDateInterval(gameRoom.getStartDate(),
            gameRoom.getEndDate());
        int questionTypeDepth = roomQuestionUtil.getQuestionTypeDepth(gameRoom.getQuestionType());

        // 기간과 깊이를 고려하여, 레벨 별로 진행될 수량을 추출한다.
        Map<Integer, Integer> levelDistribution = roomQuestionUtil.calculateLevelDistribution(dateInterval,
            questionTypeDepth);

        // 뽑은 Question 목록을 레벨별 수량에 맞춰 추출한다.
        List<Question> targetQuestionList = extractTargetQuestionList(questionTypeDepth, levelDistribution);

        // 추출한 targetQuestionList를 RoomQuestion Entity로 변환한다.
        List<RoomQuestion> roomQuestionList = IntStream.range(0, targetQuestionList.size())
            .mapToObj(
                index -> roomQuestionUtil.convertToRoomQuestion(targetQuestionList.get(index), gameRoom, index + 1))
            .collect(Collectors.toList());

        roomQuestionReposiotry.saveAll(roomQuestionList); // roomQuestion 목록 저장
    }

    @Override
    public List<Question> extractTargetQuestionList(int questionTypeDepth,
        Map<Integer, Integer> levelDistribution) {

        // 미리 선정해둔 Question 목록에서 레벨과 질문 수량에 맞게 DB에서 가져온다.
        // - Question 목록에서 Level 수량을 "랜덤"하게 셔플하여 가져온다.

        List<Question> targetQuestionList = new ArrayList<>();
        for (int level = 1; level <= questionTypeDepth; level++) {
            // Level 별 Question 목록을 level 별로 가져온다.
            List<Question> targetQuestionListAtLevel = questionRepository.findByLevel(level);
            // Question 목록을 분배 수량 만큼 <랜덤>으로 가져온다.
            targetQuestionList.addAll(
                roomQuestionUtil.getRandomQuestion(
                    targetQuestionListAtLevel,
                    levelDistribution.get(level)
                ));
        }
        return targetQuestionList;
    }

    @Override
    public DailyQuestionDto getDailyQuestion(String roomUid) {

        /**
         * RoomQuestion 관련해서, roomUid와 Sequence를 기준으로 목표하는 값만 가져오려면
         * 연관관계에 있는 Entity를 조회해서 room_id를 꺼내 다시 game_room_question.room_id를 조회하는 방법이 아닌 (불필요한 조회쿼리가 2회있음)
         * room_uid, sequence로 조회하는 방법이 필요하다.
         * -> referecedColumn 을 해서 fk를 room_uid에게도 주는 방식을 사용해야 할 것 같은데,
         * refactor 하다보니, entity 관계가 깨져, 에러가 생겼다.
         * -> 월까지 API가 어느정도 나와야 하기 때문에 구체적인 쿼리 변경은 추후 작업에서 개선하도록 할 것 -> Entity 연관관계
         * */
        GameRoom gameRoom = gameRoomService.getGameRoom(roomUid);
        int dateInterval = CustomDateUtil.getDateInterval(LocalDate.now(), gameRoom.getEndDate());

        for (RoomQuestion roomQuestion : gameRoom.getRoomQuestionList()) {
            if (roomQuestion.getDate().equals(LocalDate.now())) {
                return roomQuestionMapper.entityToDailyQuestionDto(roomQuestion);
            }
        }
        return null;
    }

    @Override
    public List<DailyQuestionDto> getDailyQuestionList(String roomUid) {
        // roomQuestion을 DailyQuestionDto로 변환한다.
        return gameRoomService.getGameRoom(roomUid).getRoomQuestionList()
            .stream()
            .map(roomQuestionMapper::entityToDailyQuestionDto)
            .collect(Collectors.toList());
    }
}
