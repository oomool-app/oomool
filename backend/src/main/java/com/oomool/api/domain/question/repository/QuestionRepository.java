package com.oomool.api.domain.question.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oomool.api.domain.question.entity.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
    List<Question> findByLevel(int level);

    Question findById(int questionId);
}
