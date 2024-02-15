package com.oomool.api.domain.feed.entity;

import static jakarta.persistence.FetchType.*;

import com.oomool.api.domain.player.entity.Player;
import com.oomool.api.domain.question.entity.RoomQuestion;
import com.oomool.api.global.entity.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "feed")
public class Feed extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // 참여하고 있는 방의 질문 정보 (1 : N 연관관계)
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "room_question_id")
    private RoomQuestion roomQuestion;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String content;

    // 작성자 = 플레이어 (1 : N 연관관계)
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "author")
    private Player author;

    public Feed update(String content) {
        this.content = content;
        return this;
    }

    @Builder
    public Feed(RoomQuestion roomQuestion, String content, Player author) {
        this.roomQuestion = roomQuestion;
        this.content = content;
        this.author = author;
    }
}
