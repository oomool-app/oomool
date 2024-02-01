package com.oomool.api.domain.question.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oomool.api.domain.question.entity.RoomQuestion;

public interface RoomQuestionReposiotry extends JpaRepository<RoomQuestion, Integer> {
}
