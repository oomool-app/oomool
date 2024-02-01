package com.oomool.api.domain.question.service;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.oomool.api.domain.question.entity.Question;
import com.oomool.api.domain.question.entity.QuestionType;
import com.oomool.api.domain.question.repository.QuestionRepository;
import com.oomool.api.domain.question.repository.RoomQuestionReposiotry;
import com.oomool.api.domain.room.dto.SettingOptionDto;
import com.oomool.api.domain.room.service.GameRoomService;

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
        // 1.

    }

    /**
     * 룸에 해당하는 질문 생성 로직
     * */
    public void publishRoomQuestionList(String roomUid) {
        SettingOptionDto settingOptionDto = gameRoomService.getSeeingOptionDto(roomUid);
        QuestionType qtype = settingOptionDto.getQuestionType();

        int dateInterval = (int)ChronoUnit.DAYS.between(settingOptionDto.getStartDate(),
            settingOptionDto.getEndDate()); // Date 빼기

        int depth = (qtype == QuestionType.AW) ? 3 : (qtype == QuestionType.BF) ? 5 : -1; // QeustionType 별 유형

        // TODO :: depth -1 이면 throw

        // 분배 수량
        int[] levelDistribution = calculateLevelDistribution(dateInterval,
            depth); // 0 ~ level + 1까지이므로 자연수 기준이다. (index 1이 level 1 과 일치하다는 의미)

        // questionList에서 level 기준으로 조회한 리스트에 distribution 개 뽑기
        List<Question> roomQuestionTargetList = new ArrayList<>();           // roomQuestion에 집어 넣을 것들 찾기
        for (int level = 1; level <= depth; level++) {
            List<Question> questionListAtLevel = questionRepository.findByLevel(level);
            roomQuestionTargetList.addAll(getRandomQuestion((questionListAtLevel), levelDistribution[level]));
        }

        /**
         * TODO
         * roomQuestionTargetList 는 레벨 별로 뽑힌 roomQuestion에 들어갈 데이터.
         * roomQuestion 엔티티에 방금 구한 roomQuestionTargetList를 순차적으로 넣으면 되는데,
         * sequence를 부여해준다.
         *
         * */

    }

    /**
     * level 별 질문 랜덤하게 섞기
     * */
    private List<Question> getRandomQuestion(List<Question> questionList, int count) {
        List<Question> shuffleList = new ArrayList<>(questionList);
        Collections.shuffle(shuffleList);
        return shuffleList.subList(0, count); // n이 questionList보다 작을 리 없게 설계되어야 한다. -> Question 여유있게.
    }

    /**
     * level 별 질문 분배
     * */
    private int[] calculateLevelDistribution(int dateInterval, int depth) {
        int[] levelDistribution = new int[depth + 1];
        int baseCount = dateInterval / depth;                // 기본적으로 할당할 수량
        for (int level = 1; level <= depth; level++) {
            int extra = level <= dateInterval % depth ? 1 : 0;
            levelDistribution[level] = baseCount + extra;
        }
        return levelDistribution;
    }

}
