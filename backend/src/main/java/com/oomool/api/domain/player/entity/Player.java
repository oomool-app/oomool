package com.oomool.api.domain.player.entity;

import static jakarta.persistence.FetchType.*;

import java.util.ArrayList;
import java.util.List;

import com.oomool.api.domain.feed.entity.Feed;
import com.oomool.api.domain.room.entity.GameRoom;
import com.oomool.api.domain.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "player")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "room_id")
    private GameRoom room;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user; // 단방향 연관관계

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "manitti_id", nullable = true)
    private int manittiId;

    @Enumerated(EnumType.STRING)
    @Column(name = "player_type", nullable = false, length = 10)
    private PlayerType playerType; // [master, player]

    @Column(nullable = true)
    private Boolean guess = null;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "avatar_id")
    private Avatar avatar; // 단방향 연관관계

    // 아바타 배경색
    @Column(name = "avatar_color", nullable = false)
    private String avatarColor;

    @OneToMany(mappedBy = "author")
    private List<Feed> feeds = new ArrayList<>(); // 양방향 연관관계

    public void updateGuess(boolean guess) {
        this.guess = guess;
    }

}
