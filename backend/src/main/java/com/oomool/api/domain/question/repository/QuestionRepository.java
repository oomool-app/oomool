package com.oomool.api.domain.question.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oomool.api.domain.question.entity.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {

    Question findById(int questionId);
}
