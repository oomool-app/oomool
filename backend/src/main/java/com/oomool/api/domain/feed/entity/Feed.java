package com.oomool.api.domain.feed.entity;

import static jakarta.persistence.FetchType.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


import com.oomool.api.domain.player.entity.Player;
import com.oomool.api.domain.question.entity.RoomQuestion;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "feed")
public class Feed {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int id;

    // 참여하고 있는 방의 질문 정보 (1 : N 연관관계)
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "room_question_id")
    private RoomQuestion roomQuestion;

    @Column(nullable = true)
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createAt; // 답변 생성 일자

    // 작성자 = 플레이어 (1 : N 연관관계)
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "author")
    private Player author;

}
