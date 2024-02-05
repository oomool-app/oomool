package com.oomool.api.domain.question.service;

import java.util.List;
import java.util.Map;

import com.oomool.api.domain.question.dto.DailyQuestionDto;
import com.oomool.api.domain.question.dto.RoomQuestionFeedDto;
import com.oomool.api.domain.question.entity.Question;
import com.oomool.api.domain.question.entity.RoomQuestion;

public interface RoomQuestionService {

    /**
     * 문답방 설정 옵션 기준으로 문답방에 질문을 생성한다.
     *
     * @param roomUid 문답방 uuid
     * */
    void publishRoomQuestionList(String roomUid);

    /**
     * 문답방에 설정된 날짜기준으로 나눈 수량에 따라 Question을 배분한다.
     *
     * @param questionTypeDepth 문답방 질문 Depth
     * @param levelDistribution 문답방 질문 분배
     * */
    List<Question> extractTargetQuestionList(int questionTypeDepth, Map<Integer, Integer> levelDistribution);

    /**
     * 데일리 질문을 조회한다.
     *
     * @param roomUid 문답방 uuid
     * */
    DailyQuestionDto getDailyQuestion(String roomUid);

    /**
     * 전체 질문 목록을 조회한다.
     *
     * @param roomUid 문답방 uuid
     * */
    List<DailyQuestionDto> getDailyQuestionList(String roomUid);

    /**
     * 방질문 정보를 반환한다.
     */
    RoomQuestionFeedDto getRoomQuestion(String roomUid, int sequence);

    /**
     *
     */
    RoomQuestion getRoomQuestionById(int roomQuestionId);
}
