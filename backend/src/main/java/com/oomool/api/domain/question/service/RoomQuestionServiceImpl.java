package com.oomool.api.domain.question.service;

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

        return null;
    }

    @Override
    public List<DailyQuestionDto> getDailyQuestionList(String roomUid) {
        // roomQuestion을 DailyQuestionDto로 변환한다.
        return gameRoomService.getGameRoom(roomUid).getRoomQuestionList()
            .stream()
            .map(roomQuestion -> DailyQuestionDto.builder()
                .question(roomQuestion.getQuestion().getQuestion())
                .sequence(roomQuestion.getSequence())
                .level(roomQuestion.getQuestion().getLevel())
                .build())
            .collect(Collectors.toList());
    }
}
