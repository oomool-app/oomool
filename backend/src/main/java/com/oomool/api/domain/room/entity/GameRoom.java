package com.oomool.api.domain.room.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.oomool.api.domain.player.entity.Player;
import com.oomool.api.domain.question.entity.QuestionType;
import com.oomool.api.domain.question.entity.RoomQuestion;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "game_room")
public class GameRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "uid", length = 10)
    private String roomUid;

    @Column(nullable = false, length = 30)
    private String title;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate startDate;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate endDate;

    @Column(nullable = false, length = 5)
    @Enumerated(EnumType.STRING)
    private QuestionType questionType; // [AW (어색한 사이), BF (친한사이)]

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createAt; // 방 생성 일자

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL) // 전체 저장
    private List<Player> players = new ArrayList<>();

    @OneToMany(mappedBy = "room")
    private List<RoomQuestion> roomQuestionList = new ArrayList<>();
}
