package com.oomool.api.domain.room.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.oomool.api.domain.player.entity.Player;
import com.oomool.api.domain.question.entity.QuestionType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "game_room")
public class Room {

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
    private QuestionType questionType; // [AW (어색한 사이), BF (친한사이)]

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createAt; // 방 생성 일자

    @OneToMany(mappedBy = "room")
    private List<Player> players = new ArrayList<>();

}
