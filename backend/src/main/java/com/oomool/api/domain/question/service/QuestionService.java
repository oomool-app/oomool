package com.oomool.api.domain.question.service;

import com.oomool.api.domain.question.dto.QuestionDto;

public interface QuestionService {

    /**
     * 질문 내용과 레벨을 반환
     */
    QuestionDto getQuestion(int questionId);
}
