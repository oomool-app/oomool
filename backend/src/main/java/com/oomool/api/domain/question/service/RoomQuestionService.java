package com.oomool.api.domain.question.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.oomool.api.domain.question.entity.Question;
import com.oomool.api.domain.question.entity.QuestionType;
import com.oomool.api.domain.question.entity.RoomQuestion;
import com.oomool.api.domain.question.repository.QuestionRepository;
import com.oomool.api.domain.question.repository.RoomQuestionReposiotry;
import com.oomool.api.domain.room.dto.SettingOptionDto;
import com.oomool.api.domain.room.entity.GameRoom;
import com.oomool.api.domain.room.service.GameRoomService;
import com.oomool.api.global.util.CustomDateUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoomQuestionService {

    private final RoomQuestionReposiotry roomQuestionReposiotry;
    private final QuestionRepository questionRepository;
    private final GameRoomService gameRoomService;

    /**
     * 데일리 질문 조회
     * */
    public void getDailyQuestion(String roomUid, int sequence) {
    }

    /**
     * 룸에 해당하는 질문 생성 로직
     * */
    public void publishRoomQuestionList(String roomUid) {
        GameRoom gameRoom = gameRoomService.getGameRoom(roomUid);
        SettingOptionDto settingOptionDto = gameRoomService.getSettingOptionDtoByRoom(gameRoom);
        CustomDateUtil dateUtil = new CustomDateUtil();
        int dateInterval = dateUtil.getDateInterval(settingOptionDto.getStartDate(), settingOptionDto.getEndDate());
        int questionTypeDepth = getQuestionTypeDepth(settingOptionDto.getQuestionType());
        // TODO :: questionTypeDepth -1 이면 throw
        Map<Integer, Integer> levelDistribution = calculateLevelDistribution(dateInterval, questionTypeDepth);
        // questionList에서 level 기준으로 조회한 리스트에 distribution 개 뽑기
        List<Question> targetQuestionList = extractTargetQuestionList(questionTypeDepth, levelDistribution);
        // 뽑은 Question 목록 RoomQuestion 엔티티에 넣기
        List<RoomQuestion> getRoomQuestionList = getRoomQuestionList(targetQuestionList, gameRoom);
        roomQuestionReposiotry.saveAll(getRoomQuestionList); // roomQuestion 목록 저장
    }

    // 문답 생성 관련 메서드

    /**
     * QuestionType의 Depth 정도 구한다. -> 3 or 5
     * */
    private int getQuestionTypeDepth(QuestionType questionType) {
        return (questionType == QuestionType.AW) ? 3 : (questionType == QuestionType.BF) ? 5 : -1;
    }

    /**
     * Question에 있는 목록 중 문답방에서 진행할 Question을 추출한다.
     * */
    private List<Question> extractTargetQuestionList(int questionTypeDepth, Map<Integer, Integer> levelDistribution) {
        List<Question> targetQuestionList = new ArrayList<>();
        for (int level = 1; level <= questionTypeDepth; level++) {
            // Level 별 Question 목록을 level 별로 가져온다.
            List<Question> targetQuesitonAtLevel = questionRepository.findByLevel(level);
            // Question 목록을 분배 수량 만큼 랜덤으로 가져온다.
            targetQuestionList.addAll(getRandomQuestion(targetQuesitonAtLevel, levelDistribution.get(level)));
        }
        return targetQuestionList;
    }

    /**
     * Question List 를 RoomQuesiton List로 변환한다.
     * targetQuestionList는 dataInterval과 일치한 수량이여야 한다. (랜덤하게 추출한 Question)
     * sequence는 index+1값이다.
     * */
    private List<RoomQuestion> getRoomQuestionList(List<Question> targetQuestionList, GameRoom gameRoom) {
        List<RoomQuestion> roomQuestionList = new ArrayList<>();
        for (int index = 0; index < targetQuestionList.size(); index++) {
            Question questionToRoom = targetQuestionList.get(index);
            RoomQuestion roomQuestion = convertToRoomQuestion(questionToRoom, gameRoom, index + 1);
            roomQuestionList.add(roomQuestion);
        }
        return roomQuestionList;
    }

    /**
     * Question Entity를 RoomQuesetion Entity로 매핑한다.
     * */
    private RoomQuestion convertToRoomQuestion(Question question, GameRoom gameRoom, int sequence) {
        return RoomQuestion.builder()
            .room(gameRoom)
            .question(question)
            .sequence(sequence)
            .build();
    }

    /**
     * level 별 질문 랜덤하게 섞기
     * */
    private List<Question> getRandomQuestion(List<Question> targetQuestionList, int count) {
        Collections.shuffle(targetQuestionList);
        return new ArrayList<>(targetQuestionList.subList(0,
            Math.min(count, targetQuestionList.size()))); // count가 questionList보다 작을 리 없게 설계되어야 한다. -> Question 여유있게.
    }

    /**
     * level 별 질문 분배
     * */
    private Map<Integer, Integer> calculateLevelDistribution(int dateInterval, int questionTypeDepth) {
        Map<Integer, Integer> levelDistribution = new HashMap<Integer, Integer>();
        int baseCount = dateInterval / questionTypeDepth;                // 기본적으로 할당할 수량
        for (int level = 1; level <= questionTypeDepth; level++) {
            int extra = level <= dateInterval % questionTypeDepth ? 1 : 0;
            levelDistribution.put(level, baseCount + extra);
        }
        return levelDistribution;
    }

}
