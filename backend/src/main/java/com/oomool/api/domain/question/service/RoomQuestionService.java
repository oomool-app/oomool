package com.oomool.api.domain.question.service;

import org.springframework.stereotype.Service;

import com.oomool.api.domain.question.dto.RoomQuestionFeedDto;
import com.oomool.api.domain.question.entity.RoomQuestion;
import com.oomool.api.domain.question.repository.RoomQuestionRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class RoomQuestionService {

    private final RoomQuestionRepository roomQuestionRepository;

    /**
     * 방질문 정보를 반환(방질문 ID, 질문 ID, sequence)
     */
    public RoomQuestionFeedDto getRoomQuestion(String roomUid, int sequence) {
        RoomQuestion roomQuestion = roomQuestionRepository.findIdsByRoomUidAndSequence(roomUid, sequence);

        RoomQuestionFeedDto roomQuestionFeedDto = RoomQuestionFeedDto
            .builder()
            .questionId(roomQuestion.getQuestion().getId())
            .roomQuestionId(roomQuestion.getId())
            .sequence(roomQuestion.getSequence())
            .build();

        return roomQuestionFeedDto;
    }

    /**
     * 방 질문 정보 반환
     */
    public RoomQuestion getRoomQuestionById(int roomQuestionId) {

        RoomQuestion roomQuestion = roomQuestionRepository.findById(roomQuestionId);

        return roomQuestion;
    }
}
