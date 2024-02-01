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
        RoomQuestionFeedDto roomQuestionFeedDto = new RoomQuestionFeedDto();
        RoomQuestion roomQuestion = roomQuestionRepository.findIdsByRoomUidAndSequence(roomUid, sequence);

        roomQuestionFeedDto.setQuestionId(roomQuestion.getQuestion().getId());
        roomQuestionFeedDto.setRoomQuestionId(roomQuestion.getId());
        roomQuestionFeedDto.setSequence(roomQuestion.getSequence());

        return roomQuestionFeedDto;
    }
}
