package com.oomool.api.domain.question.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oomool.api.domain.question.entity.RoomQuestion;

@Repository
public interface RoomQuestionReposiotry extends JpaRepository<RoomQuestion, Integer> {
    RoomQuestion findById(int roomQuestionId);
}
