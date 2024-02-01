package com.oomool.api.domain.question.service;

import org.springframework.stereotype.Service;

import com.oomool.api.domain.question.dto.QuestionCreationDto;
import com.oomool.api.domain.question.repository.RoomQuestionReposiotry;
import com.oomool.api.domain.room.repository.GameRoomRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoomQuestionService {

    private final RoomQuestionReposiotry roomQuestionReposiotry;
    private final GameRoomRepository gameRoomRepository;

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
        QuestionCreationDto questionCreationDto = gameRoomRepository.findQuestionCreationByRoomUid(roomUid);

        // Date 빼기

        // QeustionType 별 유형

    }

}
