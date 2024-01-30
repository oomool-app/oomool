package com.oomool.api.domain.question.entity;

import static jakarta.persistence.FetchType.*;

import java.util.ArrayList;
import java.util.List;

import com.oomool.api.domain.feed.entity.Feed;
import com.oomool.api.domain.room.entity.GameRoom;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "game_room_question")
public class RoomQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // 질문방 & 방질문 -> 질문방에서 방질문을 참조
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "room_id")
    private GameRoom room; // 단방향 연관관계

    // 방 질문 & 전체 질문의 관계
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "question_id")
    private Question question; // 단방향 연관관계

    @Column(name = "seq", nullable = false)
    private int sequence; // 순서

    // 한개의 질문은 여러개의 답변을 갖는다. (1 : N 연관관계)
    @OneToMany(mappedBy = "roomQuestion")
    private List<Feed> feeds = new ArrayList<>(); // 양방향 연관관계
}
