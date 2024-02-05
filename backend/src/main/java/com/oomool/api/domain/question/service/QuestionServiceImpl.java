package com.oomool.api.domain.question.service;

import org.springframework.stereotype.Service;

import com.oomool.api.domain.question.dto.QuestionDto;
import com.oomool.api.domain.question.entity.Question;
import com.oomool.api.domain.question.repository.QuestionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;

    /**
     * 질문 내용과 레벨을 반환합니다.
     */
    @Override
    public QuestionDto getQuestion(int questionId) {
        Question question = questionRepository.findById(questionId);

        QuestionDto questionDto = QuestionDto
            .builder()
            .question(question.getQuestion())
            .level(question.getLevel())
            .build();

        return questionDto;
    }

}
