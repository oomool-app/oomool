package com.oomool.api.domain.question.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oomool.api.domain.question.entity.RoomQuestion;

@Repository
public interface RoomQuestionRepository extends JpaRepository<RoomQuestion, Integer> {
    List<RoomQuestion> findAll();

    RoomQuestion findById(int roomQuestionId);
}
