package com.oomool.api.domain.question.util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.oomool.api.domain.question.entity.Question;
import com.oomool.api.domain.question.entity.QuestionType;
import com.oomool.api.domain.question.entity.RoomQuestion;
import com.oomool.api.domain.room.entity.GameRoom;

/**
 * 전체 Question 목록에서 문답방에서 사용할 RoomQuestion 목록을 생성하는데, 사용하는 Util
 * */

@Component
public class RoomQuestionUtil {

    /**
     * QuestionType의 Depth 정도 구한다. -> 3 or 5
     *
     * @param questionType 질문 유형
     * */
    public int getQuestionTypeDepth(QuestionType questionType) {
        return (questionType == QuestionType.AW) ? 3 : (questionType == QuestionType.BF) ? 5 : -1;
    }

    /**
     * level 별 질문 분배
     *
     * @param dateInterval 날짜 기간
     * @param questionTypeDepth 질문 깊이
     * */
    public Map<Integer, Integer> calculateLevelDistribution(int dateInterval, int questionTypeDepth) {
        Map<Integer, Integer> levelDistribution = new HashMap<Integer, Integer>();
        int baseCount = dateInterval / questionTypeDepth;                // 기본적으로 할당할 수량
        for (int level = 1; level <= questionTypeDepth; level++) {
            int extra = level <= dateInterval % questionTypeDepth ? 1 : 0;
            levelDistribution.put(level, baseCount + extra);
        }
        return levelDistribution;
    }

    /**
     * Question Entity를 RoomQuesetion Entity로 매핑한다.
     *
     * @param question 질문
     * @param gameRoom 문답방
     * @param sequence 순서
     * */
    public RoomQuestion convertToRoomQuestion(Question question, GameRoom gameRoom, int sequence) {
        LocalDate startDate = gameRoom.getStartDate();
        return RoomQuestion.builder()
            .room(gameRoom)
            .question(question)
            .sequence(sequence)
            .date(startDate.plusDays(sequence - 1))
            .build();
    }

    /**
     * level 별 질문 랜덤하게 섞기
     *
     * @param targetQuestionList 변환할 질문 목록
     * @param count level 별로 분배된 수량
     * */
    public List<Question> getRandomQuestion(List<Question> targetQuestionList, int count) {
        Collections.shuffle(targetQuestionList);
        return new ArrayList<>(targetQuestionList.subList(0,
            Math.min(count, targetQuestionList.size()))); // count가 questionList보다 작을 리 없게 설계되어야 한다. -> Question 여유있게.
    }
}
